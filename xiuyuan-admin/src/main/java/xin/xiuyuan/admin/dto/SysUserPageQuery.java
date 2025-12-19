package xin.xiuyuan.admin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.UserSex;
import xin.xiuyuan.common.types.UserType;

/**
 * 用户分页查询对象
 *
 * @author xinbaojian
 * @create 2025-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserPageQuery extends BasePageQuery {

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
     * 用户性别
     */
    private UserSex userSex;

    /**
     * 状态
     */
    private CommonStatus status;
}