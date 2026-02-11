package xin.xiuyuan.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.dto.user.*;
import xin.xiuyuan.admin.service.ISysUserService;
import xin.xiuyuan.admin.vo.SysUserPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.constant.RoleConstant;

/**
 * 用户管理
 *
 * @author xinbaojian
 * @create 2025-12-15 17:35
 **/
@RestController
@RequestMapping("/admin/user")
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
    @SaCheckPermission(value = "setting:user:add", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
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
    @SaCheckPermission(value = "setting:user:update", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
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
    @SaCheckPermission(value = "setting:user:delete", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
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
    @SaCheckPermission(value = "setting:user:list", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<PageData<SysUserPageVO>> list(SysUserPageQuery pageQuery) {
        return userService.list(pageQuery);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/userInfo")
    public ApiResult<UserInfoVo> getUserInfo() {
        return userService.getUserInfo();
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPwd/{id}")
    @SaCheckPermission(value = "setting:user:resetPwd", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<String> resetPwd(@PathVariable String id, @RequestBody @Validated UserResetPwd form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        form.setId(id);
        return userService.resetPwd(form);
    }

    /**
     * 查询用户信息
     */
    @GetMapping("/{id}")
    @SaCheckPermission(value = "setting:user:list", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<SysUserVO> findById(@PathVariable String id) {
        return ApiResult.success(userService.findVoById(id));
    }

    /**
     * 查询当前用户信息
     */
    @GetMapping("/current")
    public ApiResult<SysUserVO> findCurrent() {
        return ApiResult.success(userService.findVoById(StpUtil.getLoginIdAsString()));
    }

    /**
     * 修改用户资料
     */
    @PutMapping("/avatar/{id}")
    public ApiResult<String> updateAvatar(@PathVariable String id, @RequestBody @Validated UserUpdateProfile form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        form.setId(id);
        return userService.updateAvatar(form);
    }
}