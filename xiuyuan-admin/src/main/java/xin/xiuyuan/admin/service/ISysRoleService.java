package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.dto.SysRoleForm;
import xin.xiuyuan.admin.dto.SysRolePageQuery;
import xin.xiuyuan.admin.vo.SysRolePageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

/**
 * 角色业务层
 *
 * @author xinbaojian
 * @date 2025-12-17
 */
public interface ISysRoleService {

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
}