package xin.xiuyuan.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.UserSex;
import xin.xiuyuan.common.types.UserType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author xinbaojian
 * @create 2025-12-15 17:14
 **/
@Data
@Document
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    /**
     * 部门 ID
     */
    private String deptId;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型
     */
    private UserType userType = UserType.NORMAL_USER;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别
     */
    private UserSex userSex = UserSex.UNKNOWN;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密的盐
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 删除标志
     */
    private Boolean deleted = Boolean.FALSE;

    /**
     * 最后登录 IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginDate;

    /**
     * 密码最后更新时间
     */
    private LocalDateTime pwdUpdateDate;
}
