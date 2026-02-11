package xin.xiuyuan.admin.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import xin.xiuyuan.admin.dto.login.LoginForm;
import xin.xiuyuan.admin.dto.user.*;
import xin.xiuyuan.admin.entity.SysUser;
import xin.xiuyuan.admin.vo.SysUserPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

import java.util.List;

/**
 * 用户 Service
 *
 * @author xinbaojian
 * @create 2025-12-15 17:34
 **/
public interface ISysUserService {

    /**
     * 保存用户
     *
     * @param form 用户表单
     * @return ApiResult
     */
    ApiResult<String> save(SysUserCreateForm form);

    /**
     * 更新用户
     *
     * @param id   用户ID
     * @param form 用户表单
     * @return ApiResult
     */
    ApiResult<String> update(String id, SysUserForm form);

    /**
     * 删除用户
     *
     * @param id 用户 ID
     * @return ApiResult
     */
    ApiResult<String> delete(String id);

    /**
     * 分页查询用户列表
     *
     * @param pageQuery 分页查询参数
     * @return 用户分页列表
     */
    ApiResult<PageData<SysUserPageVO>> list(SysUserPageQuery pageQuery);

    /**
     * 登录
     *
     * @param form 登录表单
     * @return ApiResult
     */
    ApiResult<SaTokenInfo> login(LoginForm form);

    /**
     * 登出
     *
     * @return ApiResult
     */
    ApiResult<String> logout();

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    ApiResult<UserInfoVo> getUserInfo();

    /**
     * 重置密码
     *
     * @param form 重置密码表单
     * @return ApiResult
     */
    ApiResult<String> resetPwd(UserResetPwd form);

    /**
     * 根据 ID 查询用户
     *
     * @param id 用户 ID
     * @return 用户
     */
    SysUser findById(String id);

    SysUserVO findVoById(String id);

    /**
     * 获取用户权限列表
     *
     * @param id 用户 ID
     * @return 权限列表
     */
    List<String> getPermissionList(String id);

    /**
     * 获取用户权限ID列表
     *
     * @param id 用户 ID
     * @return 权限ID列表
     */
    List<String> getPermissionIdList(String id);

    /**
     * 获取用户角色编码列表
     *
     * @param id 用户 ID
     * @return 角色列表
     */
    List<String> getRoleKeys(String id);

    /**
     * 更新用户头像
     *
     * @param form 更新用户头像表单
     * @return ApiResult
     */
    ApiResult<String> updateAvatar(UserUpdateProfile form);

}