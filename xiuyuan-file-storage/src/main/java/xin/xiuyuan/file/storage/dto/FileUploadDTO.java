package xin.xiuyuan.file.storage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传请求DTO
 *
 * @author xiuyuan
 */
@Data
public class FileUploadDTO {

    /**
     * 文件路径前缀（可选，如：images/2024/）
     */
    private String pathPrefix;

    /**
     * 自定义文件名（可选，不设置则使用原文件名）
     */
    private String fileName;

    /**
     * 存储桶名称（可选，不设置则使用默认桶）
     */
    @NotBlank(message = "存储桶名称不能为空")
    private String bucketName;

    /**
     * 是否使用随机文件名（默认true）
     */
    private Boolean useRandomFileName = true;

    /**
     * 是否覆盖同名文件（默认false）
     */
    private Boolean overwrite = false;

    /**
     * 自定义元数据（可选）
     */
    private Map<String, String> metadata = new HashMap<>();

    /**
     * 内容类型（可选，不设置则自动识别）
     */
    private String contentType;
}
