package xin.xiuyuan.admin.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.UserSex;
import xin.xiuyuan.common.types.UserType;

/**
 * 用户表单对象
 *
 * @author xinbaojian
 * @create 2025-12-19
 */
@Data
@Accessors(chain = true)
public class SysUserForm {

    /**
     * 部门 ID
     */
    private String deptId;

    /**
     * 登录账号
     */
    @NotBlank(message = "登录账号不能为空")
    private String loginName;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 用户类型
     */
    private UserType userType = UserType.NORMAL_USER;

    /**
     * 邮箱
     */
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号码
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
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
     * 状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 备注
     */
    private String remark;
}