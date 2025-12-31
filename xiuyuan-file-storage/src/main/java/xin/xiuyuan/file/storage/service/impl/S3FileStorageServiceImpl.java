package xin.xiuyuan.file.storage.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import xin.xiuyuan.file.storage.config.FileStorageProperties;
import xin.xiuyuan.file.storage.dto.FileInfoVO;
import xin.xiuyuan.file.storage.dto.FileUploadDTO;
import xin.xiuyuan.file.storage.exception.FileStorageException;
import xin.xiuyuan.file.storage.service.FileStorageService;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * S3 协议兼容的文件存储服务实现
 * 支持 RustFS、MinIO、AWS S3
 *
 * @author xiuyuan
 */
@Slf4j
@RequiredArgsConstructor
public class S3FileStorageServiceImpl implements FileStorageService {

    private final S3Client s3Client;
    private final FileStorageProperties properties;

    /**
     * 初始化时确保 bucket 存在
     */
    @jakarta.annotation.PostConstruct
    public void init() {
        String bucketName = properties.getBucketName();
        if (bucketName != null && !bucketName.isEmpty()) {
            ensureBucketExists(bucketName);
        }
    }

    @Override
    public FileInfoVO upload(MultipartFile file) {
        return upload(file, true, false);
    }

    @Override
    public FileInfoVO upload(MultipartFile file, Boolean useRandomFileName, Boolean overwrite) {
        try (InputStream inputStream = file.getInputStream()) {
            // 自动生成日期前缀：yyyyMMdd
            String datePrefix = java.time.LocalDate.now().format(
                    java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")
            );

            // 构建 DTO
            FileUploadDTO dto = new FileUploadDTO();
            dto.setBucketName(properties.getBucketName());
            dto.setPathPrefix(datePrefix);
            dto.setUseRandomFileName(useRandomFileName);
            dto.setOverwrite(overwrite);

            return upload(inputStream, file.getOriginalFilename(), file.getContentType(), dto);
        } catch (Exception e) {
            log.error("文件上传失败: fileName={}, error={}", file.getOriginalFilename(), e.getMessage(), e);
            throw new FileStorageException("文件上传失败: " + e.getMessage(), e);
        }
    }

    public FileInfoVO upload(MultipartFile file, FileUploadDTO dto) {
        try (InputStream inputStream = file.getInputStream()) {
            return upload(inputStream, file.getOriginalFilename(), file.getContentType(), dto);
        } catch (Exception e) {
            log.error("文件上传失败: bucket={}, fileName={}, error={}",
                    dto.getBucketName(), file.getOriginalFilename(), e.getMessage(), e);

            // 提供更详细的错误信息
            String errorMsg = e.getMessage();
            if (errorMsg.contains("403")) {
                throw new FileStorageException("FILE_UPLOAD_FORBIDDEN",
                        "文件上传失败(403): 可能原因：1) Bucket '" + dto.getBucketName() + "' 不存在，请先在 RustFS 中创建；2) Access Key/Secret Key 错误；3) 用户权限不足", e);
            } else if (errorMsg.contains("404")) {
                throw new FileStorageException("FILE_UPLOAD_NOT_FOUND",
                        "文件上传失败(404): Bucket '" + dto.getBucketName() + "' 不存在或端点配置错误", e);
            }
            throw new FileStorageException("文件上传失败: " + e.getMessage(), e);
        }
    }

    @Override
    public FileInfoVO upload(InputStream inputStream, String fileName, String contentType, FileUploadDTO dto) {
        try {
            // 生成对象名称
            String objectName = generateObjectName(fileName, dto);

            // 读取流内容并创建请求体
            byte[] bytes = IoUtil.readBytes(inputStream);
            RequestBody requestBody = RequestBody.fromBytes(bytes);

            // 构建请求（不使用 contentType 和 metadata，避免 RustFS 403 错误）
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(dto.getBucketName())
                    .key(objectName)
                    .build();

            // 上传文件
            PutObjectResponse response = s3Client.putObject(request, requestBody);

            // 获取文件信息
            FileInfoVO fileInfo = getFileInfo(dto.getBucketName(), objectName);
            fileInfo.setOriginalFileName(fileName);

            log.info("文件上传成功: bucket={}, objectName={}, etag={}",
                    dto.getBucketName(), objectName, response.eTag());

            return fileInfo;

        } catch (FileStorageException e) {
            throw e;
        } catch (software.amazon.awssdk.services.s3.model.S3Exception e) {
            // 处理 S3 特定的异常
            log.error("S3 上传失败: bucket={}, fileName={}, statusCode={}, message={}",
                    dto.getBucketName(), fileName, e.statusCode(), e.getMessage());

            String errorMsg;
            if (e.statusCode() == 403) {
                errorMsg = "文件上传失败(403): Bucket '" + dto.getBucketName() + "' 访问被拒绝。请检查：1) Access Key/Secret Key 是否正确；2) 用户是否有写权限；3) Bucket 是否存在";
            } else if (e.statusCode() == 404) {
                errorMsg = "文件上传失败(404): Bucket '" + dto.getBucketName() + "' 不存在";
            } else {
                errorMsg = "文件上传失败(" + e.statusCode() + "): " + e.getMessage();
            }
            throw new FileStorageException("FILE_UPLOAD_S3_ERROR", errorMsg, e);
        } catch (Exception e) {
            log.error("文件上传失败: bucket={}, fileName={}, error={}",
                    dto.getBucketName(), fileName, e.getMessage(), e);
            throw new FileStorageException("FILE_UPLOAD_ERROR", "文件上传失败: " + e.getMessage(), e);
        }
    }

    @Override
    public InputStream download(String objectName) {
        try {
            String bucketName = properties.getBucketName();

            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build();

            return s3Client.getObject(request);

        } catch (Exception e) {
            log.error("文件下载失败: objectName={}, error={}", objectName, e.getMessage(), e);
            throw new FileStorageException("FILE_DOWNLOAD_ERROR", "文件下载失败: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(String objectName) {
        try {
            String bucketName = properties.getBucketName();

            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build();

            s3Client.deleteObject(request);

            log.info("文件删除成功: bucket={}, objectName={}", bucketName, objectName);
            return true;

        } catch (Exception e) {
            log.error("文件删除失败: objectName={}, error={}", objectName, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public int deleteBatch(List<String> objectNames) {
        try {
            String bucketName = properties.getBucketName();

            // 构建删除对象列表
            List<ObjectIdentifier> objects = objectNames.stream()
                    .map(name -> ObjectIdentifier.builder().key(name).build())
                    .toList();

            // 批量删除（每次最多1000个）
            int deletedCount = 0;
            for (int i = 0; i < objects.size(); i += 1000) {
                int endIndex = Math.min(i + 1000, objects.size());
                List<ObjectIdentifier> batch = objects.subList(i, endIndex);

                DeleteObjectsRequest request = DeleteObjectsRequest.builder()
                        .bucket(bucketName)
                        .delete(Delete.builder().objects(batch).build())
                        .build();

                DeleteObjectsResponse response = s3Client.deleteObjects(request);
                deletedCount += response.deleted().size();
            }

            log.info("批量删除文件完成: bucket={}, count={}", bucketName, deletedCount);
            return deletedCount;

        } catch (Exception e) {
            log.error("批量删除文件失败: error={}", e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public FileInfoVO getFileInfo(String objectName) {
        try {
            return getFileInfo(properties.getBucketName(), objectName);
        } catch (Exception e) {
            log.error("获取文件信息失败: objectName={}, error={}", objectName, e.getMessage(), e);
            throw new FileStorageException("FILE_INFO_ERROR", "获取文件信息失败: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean exists(String objectName) {
        try {
            String bucketName = properties.getBucketName();

            HeadObjectRequest request = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build();

            s3Client.headObject(request);
            return true;

        } catch (NoSuchKeyException e) {
            return false;
        } catch (Exception e) {
            log.error("检查文件存在性失败: objectName={}, error={}", objectName, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public String getFileUrl(String objectName) {
        try {
            String bucketName = properties.getBucketName();

            // 如果配置了自定义域名，使用自定义域名
            if (properties.getCustomDomain() != null && !properties.getCustomDomain().isEmpty()) {
                return properties.getCustomDomain() + "/" + objectName;
            }

            // 否则使用预签名URL（默认7天有效期）
            return getPresignedUrl(objectName, (int) TimeUnit.DAYS.toSeconds(7));

        } catch (Exception e) {
            log.error("获取文件URL失败: objectName={}, error={}", objectName, e.getMessage(), e);
            throw new FileStorageException("FILE_URL_ERROR", "获取文件URL失败: " + e.getMessage(), e);
        }
    }

    @Override
    public String getPresignedUrl(String objectName, int expireSeconds) {
        try {
            String bucketName = properties.getBucketName();
            var s3Config = properties.getS3();

            // 配置预签名器，使用和 S3Client 相同的配置
            software.amazon.awssdk.auth.credentials.AwsBasicCredentials credentials =
                    software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create(
                            s3Config.getAccessKeyId(),
                            s3Config.getSecretAccessKey()
                    );

            software.amazon.awssdk.services.s3.presigner.S3Presigner presigner =
                    software.amazon.awssdk.services.s3.presigner.S3Presigner.builder()
                            .credentialsProvider(
                                    software.amazon.awssdk.auth.credentials.StaticCredentialsProvider.create(credentials)
                            )
                            .region(software.amazon.awssdk.regions.Region.of(s3Config.getRegion()))
                            .endpointOverride(java.net.URI.create(s3Config.getEndpoint()))
                            .build();

            java.net.URL url = presigner.presignGetObject(builder -> builder
                    .signatureDuration(java.time.Duration.ofSeconds(expireSeconds))
                    .getObjectRequest(objectRequestBuilder -> objectRequestBuilder
                            .bucket(bucketName)
                            .key(objectName)
                    )
            ).url();

            presigner.close();

            return url.toString();

        } catch (Exception e) {
            log.error("获取预签名URL失败: objectName={}, error={}", objectName, e.getMessage(), e);
            throw new FileStorageException("PRESIGNED_URL_ERROR", "获取预签名URL失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成对象名称
     */
    private String generateObjectName(String originalFileName, FileUploadDTO dto) {
        String fileName = dto.getFileName();

        // 如果没有指定文件名，使用原文件名或随机文件名
        if (fileName == null || fileName.isEmpty()) {
            if (Boolean.TRUE.equals(dto.getUseRandomFileName())) {
                // 生成随机文件名：时间戳_UUID.扩展名
                String extension = getFileExtension(originalFileName);
                fileName = System.currentTimeMillis() + "_" + IdUtil.simpleUUID() + extension;
            } else {
                fileName = originalFileName;
            }
        }

        // 添加路径前缀
        if (dto.getPathPrefix() != null && !dto.getPathPrefix().isEmpty()) {
            String prefix = dto.getPathPrefix();
            if (!prefix.endsWith("/")) {
                prefix += "/";
            }
            fileName = prefix + fileName;
        }

        // 添加基础路径
        if (properties.getBasePath() != null && !properties.getBasePath().isEmpty()) {
            String basePath = properties.getBasePath();
            if (!basePath.endsWith("/")) {
                basePath += "/";
            }
            fileName = basePath + fileName;
        }

        return fileName;
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        return lastDotIndex > 0 ? fileName.substring(lastDotIndex) : "";
    }

    /**
     * 确保 bucket 存在，如果不存在则创建
     */
    private void ensureBucketExists(String bucketName) {
        try {
            // 检查 bucket 是否存在
            HeadBucketRequest headRequest = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3Client.headBucket(headRequest);

        } catch (NoSuchBucketException e) {
            // Bucket 不存在，尝试创建
            try {
                CreateBucketRequest createRequest = CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .build();

                s3Client.createBucket(createRequest);
                log.info("Bucket {} 创建成功", bucketName);

            } catch (Exception createException) {
                log.error("创建 bucket {} 失败: {}", bucketName, createException.getMessage());
                throw new FileStorageException("BUCKET_CREATE_ERROR",
                        "无法创建 bucket '" + bucketName + "': " + createException.getMessage(), createException);
            }
        } catch (software.amazon.awssdk.services.s3.model.S3Exception e) {
            // 如果是 403，可能是权限问题，但也可能 bucket 存在
            if (e.statusCode() == 403) {
                log.debug("检查 bucket {} 时收到 403，可能存在但无 headBucket 权限", bucketName);
            }
        }
    }

    /**
     * 获取文件信息
     */
    private FileInfoVO getFileInfo(String bucketName, String objectName) {
        HeadObjectRequest headRequest = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        HeadObjectResponse headResponse = s3Client.headObject(headRequest);

        return FileInfoVO.builder()
                .objectName(objectName)
                .fileSize(headResponse.contentLength())
                .contentType(headResponse.contentType())
                .bucketName(bucketName)
                .fileUrl(getFileUrl(objectName))
                .etag(headResponse.eTag())
                .uploadTime(LocalDateTime.ofInstant(
                        headResponse.lastModified(),
                        ZoneOffset.systemDefault()
                ))
                .metadata(headResponse.metadata())
                .build();
    }
}
