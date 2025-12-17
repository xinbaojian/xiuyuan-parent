package xin.xiuyuan.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.dto.post.SysPostForm;
import xin.xiuyuan.admin.dto.post.SysPostPageQuery;
import xin.xiuyuan.admin.service.ISysPostService;
import xin.xiuyuan.admin.vo.post.SysPostPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

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
    public ApiResult<PageData<SysPostPageVO>> list(SysPostPageQuery pageQuery) {
        return postService.list(pageQuery);
    }
}