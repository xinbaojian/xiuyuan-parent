package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.dto.menu.MenuPermissionForm;
import xin.xiuyuan.admin.vo.menu.MenuTreeVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.MenuType;

import java.util.List;

/**
 * 菜单权限 Service
 *
 * @author xinbaojian
 * @create 2025-12-29 11:35
 **/
public interface IMenuPermissionService {

    /**
     * 保存菜单权限
     *
     * @param form 菜单权限表单
     * @return ApiResult
     */
    ApiResult<String> save(MenuPermissionForm form);

    /**
     * 更新菜单权限
     *
     * @param id   菜单权限 ID
     * @param form 菜单权限表单
     * @return ApiResult
     */
    ApiResult<String> update(String id, MenuPermissionForm form);

    /**
     * 删除菜单权限
     *
     * @param id 菜单权限 ID
     * @return ApiResult
     */
    ApiResult<String> delete(String id);

    /**
     * 获取菜单树
     *
     * @param title    菜单标题
     * @param status   菜单状态
     * @param menuType 菜单类型
     * @return 菜单树
     */
    ApiResult<List<MenuTreeVO>> getMenuTree(String title, CommonStatus status, MenuType menuType);

    /**
     * 获取当前用户菜单树
     *
     * @return 菜单树
     */
    ApiResult<List<MenuTreeVO>> getCurrentMenuTree();
}
