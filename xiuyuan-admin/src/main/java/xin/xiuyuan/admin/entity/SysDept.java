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

/**
 * 部门表
 *
 * @author xinbaojian
 * @create 2025-12-15 17:56
 **/
@Data
@Document
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    /**
     * 父部门 id
     */
    private String parentId = "00";

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum = 0;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 删除标志
     */
    private Boolean delFlag = false;
}