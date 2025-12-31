package xin.xiuyuan.file.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传结果VO
 *
 * @author xiuyuan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResultVO {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 消息
     */
    private String message;

    /**
     * 文件信息
     */
    private FileInfoVO fileInfo;

    /**
     * 错误代码（失败时返回）
     */
    private String errorCode;
}
