package xin.xiuyuan.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.dto.SysRoleForm;
import xin.xiuyuan.admin.dto.SysRolePageQuery;
import xin.xiuyuan.admin.service.ISysRoleService;
import xin.xiuyuan.admin.vo.SysRolePageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

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
    public ApiResult<PageData<SysRolePageVO>> list(SysRolePageQuery pageQuery) {
        return sysRoleService.list(pageQuery);
    }
}