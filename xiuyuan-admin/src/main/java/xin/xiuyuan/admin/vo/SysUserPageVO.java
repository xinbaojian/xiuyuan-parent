package xin.xiuyuan.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.UserSex;
import xin.xiuyuan.common.types.UserType;

import java.time.LocalDateTime;

/**
 * 用户分页视图对象
 *
 * @author xinbaojian
 * @create 2025-12-19
 */
@Data
public class SysUserPageVO {

    /**
     * 主键 ID
     */
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
    private UserType userType;

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
    private UserSex userSex;

    /**
     * 状态
     */
    private CommonStatus status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    public String getUserTypeDesc() {
        return userType == null ? null : userType.getDesc();
    }

    public String getUserSexDesc() {
        return userSex == null ? null : userSex.getDesc();
    }

    public String getStatusDesc() {
        return status == null ? null : status.getDesc();
    }
}