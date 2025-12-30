package xin.xiuyuan.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.dto.post.SysPostForm;
import xin.xiuyuan.admin.dto.post.SysPostPageQuery;
import xin.xiuyuan.admin.service.ISysPostService;
import xin.xiuyuan.admin.vo.post.SysPostPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.Option;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.constant.RoleConstant;

import java.util.List;

/**
 * 岗位管理
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class SysPostController {

    private final ISysPostService postService;


    /**
     * 新增岗位
     *
     * @param form 岗位表单
     * @return 新增结果
     */
    @PostMapping
    @SaCheckPermission(value = "setting:post:add", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<String> save(@RequestBody @Validated SysPostForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return postService.save(form);
    }

    /**
     * 修改岗位
     *
     * @param id   岗位 ID
     * @param form 岗位表单
     * @return 修改结果
     */
    @PostMapping("/{id}")
    @SaCheckPermission(value = "setting:post:edit", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<String> update(@PathVariable String id, @RequestBody @Validated SysPostForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return postService.update(id, form);
    }

    /**
     * 删除岗位
     *
     * @param id 岗位 ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission(value = "setting:post:delete", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<String> delete(@PathVariable String id) {
        return postService.delete(id);
    }

    /**
     * 分页查询岗位列表
     *
     * @param pageQuery 分页查询参数
     * @return 岗位分页列表
     */
    @GetMapping("/page")
    @SaCheckPermission(value = "setting:post:list", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<PageData<SysPostPageVO>> list(SysPostPageQuery pageQuery) {
        return postService.list(pageQuery);
    }

    /**
     * 获取岗位下拉列表
     *
     * @return 岗位下拉列表
     */
    @GetMapping("/options")
    public ApiResult<List<Option>> options() {
        return postService.options();
    }
}