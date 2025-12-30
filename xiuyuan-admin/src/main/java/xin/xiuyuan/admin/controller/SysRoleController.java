package xin.xiuyuan.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.dto.role.SysRoleForm;
import xin.xiuyuan.admin.dto.role.SysRolePageQuery;
import xin.xiuyuan.admin.service.ISysRoleService;
import xin.xiuyuan.admin.vo.SysRolePageVO;
import xin.xiuyuan.admin.vo.permission.SysMenuPermissionVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.Option;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.constant.RoleConstant;

import java.util.List;

/**
 * 角色管理
 *
 * @author xinbaojian
 * @date 2025-12-17
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final ISysRoleService sysRoleService;


    /**
     * 新增角色
     *
     * @param form 角色表单
     * @return 新增结果
     */
    @PostMapping
    @SaCheckPermission(value = "menu:setting:role:add", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<String> save(@RequestBody @Validated SysRoleForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return sysRoleService.save(form);
    }

    /**
     * 修改角色
     *
     * @param id   角色 ID
     * @param form 角色表单
     * @return 修改结果
     */
    @PostMapping("/{id}")
    @SaCheckPermission(value = "menu:setting:role:update", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<String> update(@PathVariable String id, @RequestBody @Validated SysRoleForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return sysRoleService.update(id, form);
    }

    /**
     * 删除角色
     *
     * @param id 角色 ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission(value = "menu:setting:role:delete", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<String> delete(@PathVariable String id) {
        return sysRoleService.delete(id);
    }

    /**
     * 分页查询角色列表
     *
     * @param pageQuery 分页查询参数
     * @return 角色分页列表
     */
    @GetMapping("/page")
    @SaCheckPermission(value = "menu:setting:role:list", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<PageData<SysRolePageVO>> list(SysRolePageQuery pageQuery) {
        return sysRoleService.list(pageQuery);
    }

    /**
     * 获取角色下拉列表
     *
     * @return 角色下拉列表
     */
    @GetMapping("/options")
    public ApiResult<List<Option>> options() {
        return sysRoleService.options();
    }

    /**
     * 设置角色权限
     *
     * @param id          角色 ID
     * @param permissions 权限列表
     * @return 设置结果
     */
    @PutMapping("/{id}/permission")
    @SaCheckPermission(value = "menu:setting:role:permission:set", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<String> setPermission(@PathVariable String id, @RequestBody List<String> permissions) {
        return sysRoleService.setPermission(id, permissions);
    }

    /**
     * 获取角色权限
     *
     * @param id 角色 ID
     * @return 角色权限列表
     */
    @GetMapping("/{id}/permission")
    public ApiResult<List<SysMenuPermissionVO>> getPermission(@PathVariable String id) {
        return sysRoleService.getPermission(id);
    }
}