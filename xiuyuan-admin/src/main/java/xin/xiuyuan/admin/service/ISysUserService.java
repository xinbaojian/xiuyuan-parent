package xin.xiuyuan.admin.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import xin.xiuyuan.admin.dto.login.LoginForm;
import xin.xiuyuan.admin.dto.user.SysUserCreateForm;
import xin.xiuyuan.admin.dto.user.SysUserForm;
import xin.xiuyuan.admin.dto.user.SysUserPageQuery;
import xin.xiuyuan.admin.dto.user.UserInfoVo;
import xin.xiuyuan.admin.vo.SysUserPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

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
}