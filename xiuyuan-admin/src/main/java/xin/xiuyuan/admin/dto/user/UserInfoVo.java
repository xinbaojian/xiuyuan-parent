package xin.xiuyuan.admin.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户信息VO
 *
 * @author xinbaojian
 * @create 2025-12-20 14:00
 **/
@Data
@Accessors(chain = true)
public class UserInfoVo {

    /**
     * 权限列表
     */
    private List<String> permissions;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar = "https://gcore.jsdelivr.net/gh/zxwk1998/image/avatar/avatar_3.png";
}
