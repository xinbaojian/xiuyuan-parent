package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.dto.role.SysRoleForm;
import xin.xiuyuan.admin.dto.role.SysRolePageQuery;
import xin.xiuyuan.admin.entity.SysRole;
import xin.xiuyuan.admin.vo.SysRolePageVO;
import xin.xiuyuan.admin.vo.permission.SysMenuPermissionVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.Option;
import xin.xiuyuan.common.common.PageData;

import java.util.List;

/**
 * 角色业务层
 *
 * @author xinbaojian
 * @date 2025-12-17
 */
public interface ISysRoleService extends IBaseService<SysRole> {

    /**
     * 保存角色
     *
     * @param form 角色表单
     * @return ApiResult
     */
    ApiResult<String> save(SysRoleForm form);

    /**
     * 更新角色
     *
     * @param id   角色ID
     * @param form 角色表单
     * @return ApiResult
     */
    ApiResult<String> update(String id, SysRoleForm form);

    /**
     * 删除角色
     *
     * @param id 角色 ID
     * @return ApiResult
     */
    ApiResult<String> delete(String id);

    /**
     * 分页查询角色列表
     *
     * @param pageQuery 分页查询参数
     * @return 角色分页列表
     */
    ApiResult<PageData<SysRolePageVO>> list(SysRolePageQuery pageQuery);

    /**
     * 获取角色下拉列表
     *
     * @return 角色下拉列表
     */
    ApiResult<List<Option>> options();

    /**
     * 设置角色权限
     *
     * @param roleId      角色 ID
     * @param permissions 权限 ID 列表
     * @return 设置结果
     */
    ApiResult<String> setPermission(String roleId, List<String> permissions);

    /**
     * 获取角色权限
     *
     * @param roleId 角色 ID
     * @return 角色权限列表
     */
    ApiResult<List<SysMenuPermissionVO>> getPermission(String roleId);
}