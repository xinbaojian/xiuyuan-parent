package xin.xiuyuan.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.dto.menu.MenuPermissionForm;
import xin.xiuyuan.admin.service.IMenuPermissionService;
import xin.xiuyuan.admin.vo.menu.MenuTreeVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.MenuType;

import java.util.List;

/**
 * 菜单权限管理
 *
 * @author xinbaojian
 * @create 2025-12-29 11:40
 **/
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuPermissionController {

    private final IMenuPermissionService menuPermissionService;

    /**
     * 新增菜单
     *
     * @param form 菜单表单
     * @return 新增结果
     */
    @PostMapping
    public ApiResult<String> save(@RequestBody @Validated MenuPermissionForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return menuPermissionService.save(form);
    }

    /**
     * 修改菜单
     *
     * @param id   菜单 ID
     * @param form 菜单表单
     * @return 修改结果
     */
    @PostMapping("/{id}")
    public ApiResult<String> update(@PathVariable String id,
                                    @RequestBody @Validated MenuPermissionForm form,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return menuPermissionService.update(id, form);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单 ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable String id) {
        return menuPermissionService.delete(id);
    }

    /**
     * 获取菜单树
     *
     * @param title    菜单标题
     * @param status   菜单状态
     * @param menuType 菜单类型
     * @return 菜单树
     */
    @GetMapping("/tree")
    public ApiResult<List<MenuTreeVO>> getMenuTree(String title, CommonStatus status, MenuType menuType) {
        return menuPermissionService.getMenuTree(title, status, menuType);
    }

    /**
     * 查询当前用户菜单列表
     */
    @GetMapping("/current/menu/tree")
    public ApiResult<List<MenuTreeVO>> getCurrentMenuTree() {
        return menuPermissionService.getCurrentMenuTree();
    }
}
