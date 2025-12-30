package xin.xiuyuan.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import xin.xiuyuan.common.types.CommonStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色信息表
 *
 * @author xinbaojian
 * @create 2025-12-17 10:14
 **/
@Data
@Document
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串(唯一)
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer orderNum = 0;

    /**
     * 角色状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 角色权限
     */
    private List<SysMenuPermission> permissions;
}
