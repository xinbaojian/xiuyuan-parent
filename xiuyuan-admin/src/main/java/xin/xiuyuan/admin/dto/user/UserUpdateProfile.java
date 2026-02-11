package xin.xiuyuan.admin.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.UserSex;

/**
 * 更新用户头像
 *
 * @author xinbaojian
 * @create 2026-02-11 18:08
 **/
@Data
@Accessors(chain = true)
public class UserUpdateProfile {

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 头像
     */
    private String avatar;

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
}
