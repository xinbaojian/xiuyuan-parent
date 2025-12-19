package xin.xiuyuan.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.dto.SysUserCreateForm;
import xin.xiuyuan.admin.dto.SysUserForm;
import xin.xiuyuan.admin.dto.SysUserPageQuery;
import xin.xiuyuan.admin.service.ISysUserService;
import xin.xiuyuan.admin.vo.SysUserPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

/**
 * 用户管理
 *
 * @author xinbaojian
 * @create 2025-12-15 17:35
 **/
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController {

    private final ISysUserService userService;


    /**
     * 新增用户
     *
     * @param form 用户表单
     * @return 新增结果
     */
    @PostMapping
    public ApiResult<String> save(@RequestBody @Validated SysUserCreateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return userService.save(form);
    }

    /**
     * 修改用户
     *
     * @param id   用户 ID
     * @param form 用户表单
     * @return 修改结果
     */
    @PostMapping("/{id}")
    public ApiResult<String> update(@PathVariable String id, @RequestBody @Validated SysUserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return userService.update(id, form);
    }

    /**
     * 删除用户
     *
     * @param id 用户 ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable String id) {
        return userService.delete(id);
    }

    /**
     * 分页查询用户列表
     *
     * @param pageQuery 分页查询参数
     * @return 用户分页列表
     */
    @GetMapping("/page")
    public ApiResult<PageData<SysUserPageVO>> list(SysUserPageQuery pageQuery) {
        return userService.list(pageQuery);
    }
}