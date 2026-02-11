package xin.xiuyuan.admin.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.UserSex;
import xin.xiuyuan.common.types.UserType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息
 *
 * @author xinbaojian
 * @create 2026-02-11 18:15
 **/
@Data
@Accessors(chain = true)
public class SysUserVO {

    /**
     * 用户 ID
     */
    private String id;

    /**
     * 部门 ID
     */
    private String deptId;
    private String deptName;

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
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 最后登录 IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginDate;

    /**
     * 密码最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pwdUpdateDate;

    /**
     * 岗位 ID
     */
    private String postId;
    private String postName;

    /**
     * 用户关联角色 ID 列表
     */
    private List<String> roleIds;
    private List<String> roleNames;
}
