package xin.xiuyuan.admin.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.LoginDevice;
import xin.xiuyuan.common.types.LoginType;

/**
 * 登录表单
 *
 * @author xinbaojian
 * @create 2025-12-19 11:40
 **/
@Data
@Accessors(chain = true)
public class LoginForm {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
//    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 登录方式
     */
    private LoginType loginType = LoginType.PASSWORD;

    /**
     * 登录设备
     */
    private LoginDevice loginDevice = LoginDevice.PC;
}
