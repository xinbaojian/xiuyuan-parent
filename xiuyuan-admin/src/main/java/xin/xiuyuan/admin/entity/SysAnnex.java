package xin.xiuyuan.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * 附件表
 *
 * @author xinbaojian
 * @create 2025-12-31 14:06
 **/
@Data
@Document
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysAnnex extends BaseEntity {

    /**
     * 主键 ID
     */
    @MongoId(value = FieldType.OBJECT_ID)
    private String id;

    /**
     * 附件原始名称
     */
    private String name;

    /**
     * 上传到 OBS 的文件名
     */
    private String objectName;

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * 附件类型
     */
    private String type;

    /**
     * 附件大小
     */
    private String size;

    /**
     * obs key
     */
    private String objectKey;

    /**
     * 附件访问地址
     */
    private String objectUrl;
}
