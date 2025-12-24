package xin.xiuyuan.admin.dto.user;

import lombok.Data;

/**
 * 用户重置密码
 *
 * @author xinbaojian
 * @create 2025-12-24 17:12
 **/
@Data
public class UserResetPwd {

    /**
     * 用户 ID
     */
    private String id;

    /**
     * 新密码
     */
    private String newPassword;
}
