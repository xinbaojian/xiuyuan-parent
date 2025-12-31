package xin.xiuyuan.file.storage.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.file.storage.dto.FileInfoVO;
import xin.xiuyuan.file.storage.dto.FileUploadDTO;
import xin.xiuyuan.file.storage.service.FileStorageService;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 文件存储
 *
 * @author xiuyuan
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileStorageController {

    private final FileStorageService fileStorageService;

    /**
     * 上传文件
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult<FileInfoVO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "useRandomFileName", required = false, defaultValue = "true") Boolean useRandomFileName,
            @RequestParam(value = "overwrite", required = false, defaultValue = "false") Boolean overwrite
    ) {
        log.info("Controller: fileName={}, size={}", file.getOriginalFilename(), file.getSize());

        // 上传文件
        FileInfoVO fileInfo = fileStorageService.upload(file, useRandomFileName, overwrite);
        return ApiResult.success(fileInfo);
    }

    /**
     * 上传文件（使用 JSON 参数）
     */
    @SaCheckPermission("file:upload")
    @PostMapping(value = "/upload-with-meta", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult<FileInfoVO> uploadFileWithMetadata(
            @RequestParam("file") MultipartFile file,
            @Valid FileUploadDTO dto
    ) {
        FileInfoVO fileInfo = fileStorageService.upload(file, dto);
        return ApiResult.success(fileInfo);
    }

    /**
     * 下载文件
     */
    @SaCheckPermission("file:download")
    @GetMapping("/download/**")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取文件路径（/** 匹配的部分）
            String requestURI = request.getRequestURI();
            String contextPath = request.getContextPath();
            String path = requestURI.substring(contextPath.length());
            String objectName = path.substring("/files/download/".length());

            log.info("下载文件: {}", objectName);

            // 获取文件流
            try (InputStream inputStream = fileStorageService.download(objectName);
                 OutputStream outputStream = response.getOutputStream()) {

                // 设置响应头
                String fileName = objectName.substring(objectName.lastIndexOf("/") + 1);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition",
                        "attachment; filename=" + java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8));

                // 复制流
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.flush();
            }

        } catch (Exception e) {
            log.error("文件下载失败: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除文件
     */
    @SaCheckPermission("file:delete")
    @DeleteMapping("/{objectName}")
    public ApiResult<Boolean> deleteFile(@PathVariable String objectName) {
        boolean success = fileStorageService.delete(objectName);
        return ApiResult.success(success);
    }

    /**
     * 批量删除文件
     */
    @SaCheckPermission("file:delete")
    @DeleteMapping("/batch")
    public ApiResult<Integer> deleteFiles(@RequestBody List<String> objectNames) {
        int count = fileStorageService.deleteBatch(objectNames);
        return ApiResult.success(count);
    }

    /**
     * 获取文件信息
     */
    @SaCheckPermission("file:info")
    @GetMapping("/info/**")
    public ApiResult<FileInfoVO> getFileInfo(HttpServletRequest request) {
        // 获取文件路径
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());
        String objectName = path.substring("/files/info/".length());

        FileInfoVO fileInfo = fileStorageService.getFileInfo(objectName);
        return ApiResult.success(fileInfo);
    }

    /**
     * 获取文件访问 URL
     */
    @GetMapping("/url/**")
    public ApiResult<String> getFileUrl(HttpServletRequest request) {
        // 获取文件路径
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());
        String objectName = path.substring("/files/url/".length());

        String url = fileStorageService.getFileUrl(objectName);
        return ApiResult.success(url);
    }

    /**
     * 获取预签名 URL
     */
    @GetMapping("/presigned-url")
    public ApiResult<String> getPresignedUrl(
            @RequestParam("objectName") String objectName,
            @RequestParam(value = "expireSeconds", required = false, defaultValue = "3600") Integer expireSeconds
    ) {
        String url = fileStorageService.getPresignedUrl(objectName, expireSeconds);
        return ApiResult.success(url);
    }

    /**
     * 检查文件是否存在
     */
    @GetMapping("/exists")
    public ApiResult<Boolean> checkFileExists(@RequestParam("objectName") String objectName) {
        boolean exists = fileStorageService.exists(objectName);
        return ApiResult.success(exists);
    }
}
