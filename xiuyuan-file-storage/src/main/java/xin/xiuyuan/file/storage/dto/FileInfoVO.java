package xin.xiuyuan.file.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 文件信息响应VO
 *
 * @author xiuyuan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoVO {

    /**
     * 对象名称（完整路径）
     */
    private String objectName;

    /**
     * 原始文件名
     */
    private String originalFileName;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 内容类型
     */
    private String contentType;

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * 文件访问URL
     */
    private String fileUrl;

    /**
     * ETag
     */
    private String etag;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 文件元数据
     */
    private Map<String, String> metadata;
}
