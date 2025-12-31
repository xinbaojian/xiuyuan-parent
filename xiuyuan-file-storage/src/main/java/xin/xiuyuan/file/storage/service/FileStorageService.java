package xin.xiuyuan.file.storage.service;

import org.springframework.web.multipart.MultipartFile;
import xin.xiuyuan.file.storage.dto.FileInfoVO;
import xin.xiuyuan.file.storage.dto.FileUploadDTO;

import java.io.InputStream;
import java.util.List;

/**
 * 文件存储服务接口
 * 支持多种存储服务（RustFS、MinIO、S3、OSS等）
 *
 * @author xiuyuan
 */
public interface FileStorageService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件信息
     */
    FileInfoVO upload(MultipartFile file);

    /**
     * 上传文件（控制版本）
     *
     * @param file              文件
     * @param useRandomFileName 是否使用随机文件名
     * @param overwrite         是否覆盖同名文件
     * @return 文件信息
     */
    FileInfoVO upload(MultipartFile file, Boolean useRandomFileName, Boolean overwrite);

    /**
     * 上传文件（使用 DTO）
     *
     * @param file 文件
     * @param dto  上传参数
     * @return 文件信息
     */
    FileInfoVO upload(MultipartFile file, FileUploadDTO dto);

    /**
     * 上传文件（通过流）
     *
     * @param inputStream 文件流
     * @param fileName    文件名
     * @param contentType 内容类型
     * @param dto         上传参数
     * @return 文件信息
     */
    FileInfoVO upload(InputStream inputStream, String fileName, String contentType, FileUploadDTO dto);

    /**
     * 下载文件
     *
     * @param objectName 对象名称（文件路径）
     * @return 文件流
     */
    InputStream download(String objectName);

    /**
     * 删除文件
     *
     * @param objectName 对象名称（文件路径）
     * @return 是否成功
     */
    boolean delete(String objectName);

    /**
     * 批量删除文件
     *
     * @param objectNames 对象名称列表
     * @return 成功删除的数量
     */
    int deleteBatch(List<String> objectNames);

    /**
     * 获取文件信息
     *
     * @param objectName 对象名称（文件路径）
     * @return 文件信息
     */
    FileInfoVO getFileInfo(String objectName);

    /**
     * 判断文件是否存在
     *
     * @param objectName 对象名称（文件路径）
     * @return 是否存在
     */
    boolean exists(String objectName);

    /**
     * 获取文件访问URL
     *
     * @param objectName 对象名称（文件路径）
     * @return 访问URL
     */
    String getFileUrl(String objectName);

    /**
     * 获取带签名的临时访问URL
     *
     * @param objectName    对象名称（文件路径）
     * @param expireSeconds 过期时间（秒）
     * @return 临时访问URL
     */
    String getPresignedUrl(String objectName, int expireSeconds);
}
