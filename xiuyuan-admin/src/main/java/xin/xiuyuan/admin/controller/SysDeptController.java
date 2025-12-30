package xin.xiuyuan.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.dto.dept.SysDeptForm;
import xin.xiuyuan.admin.dto.dept.SysDeptPageQuery;
import xin.xiuyuan.admin.service.ISysDeptService;
import xin.xiuyuan.admin.vo.dept.SysDeptPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.types.CommonStatus;

import java.util.List;

/**
 * 部门管理
 *
 * @author xinbaojian
 * @create 2025-12-15 18:03
 **/
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final ISysDeptService deptService;


    /**
     * 新增部门
     *
     * @param form 部门表单
     * @return 新增结果
     */
    @PostMapping
    @SaCheckPermission(value = "sys:dept:add")
    public ApiResult<String> save(@RequestBody @Validated SysDeptForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return deptService.save(form);
    }

    /**
     * 修改部门
     *
     * @param id   部门 ID
     * @param form 部门表单
     * @return 修改结果
     */
    @PostMapping("/{id}")
    public ApiResult<String> update(@PathVariable String id, @RequestBody @Validated SysDeptForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return deptService.update(id, form);
    }

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable String id) {
        return deptService.delete(id);
    }

    /**
     * 分页查询部门列表
     *
     * @param pageQuery 分页查询参数
     * @return 部门分页列表
     */
    @GetMapping("/page")
    public ApiResult<PageData<SysDeptPageVO>> list(SysDeptPageQuery pageQuery) {
        return deptService.list(pageQuery);
    }

    /**
     * 获取部门树
     *
     * @param deptName 部门名称
     * @param status   部门状态
     * @return 部门树
     */
    @GetMapping("/tree")
    public ApiResult<List<Tree<String>>> getDeptTree(String deptName, CommonStatus status) {
        return deptService.getDeptTree(deptName, status);
    }
}