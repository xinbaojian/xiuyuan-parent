package xin.xiuyuan.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.annotation.OperationLog;
import xin.xiuyuan.admin.dto.birthday.SysBirthdayForm;
import xin.xiuyuan.admin.dto.birthday.SysBirthdayPageQuery;
import xin.xiuyuan.admin.service.ISysBirthdayService;
import xin.xiuyuan.admin.vo.birthday.SysBirthdayPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.constant.RoleConstant;

/**
 * 生日管理
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@RestController
@RequestMapping("/admin/birthday")
@RequiredArgsConstructor
public class SysBirthdayController {

    private final ISysBirthdayService birthdayService;

    /**
     * 新增生日
     *
     * @param form 生日表单
     * @return 新增结果
     */
    @PostMapping
    @SaCheckPermission(value = "birthday:add", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    @OperationLog(module = "生日管理", operationType = "新增", description = "新增生日")
    public ApiResult<String> save(@RequestBody @Validated SysBirthdayForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return birthdayService.save(form);
    }

    /**
     * 修改生日
     *
     * @param id   生日 ID
     * @param form 生日表单
     * @return 修改结果
     */
    @PutMapping("/{id}")
    @SaCheckPermission(value = "birthday:edit", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    @OperationLog(module = "生日管理", operationType = "修改", description = "修改生日ID: #id")
    public ApiResult<String> update(@PathVariable String id, @RequestBody @Validated SysBirthdayForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return birthdayService.update(id, form);
    }

    /**
     * 删除生日
     *
     * @param id 生日 ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission(value = "birthday:delete", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    @OperationLog(module = "生日管理", operationType = "删除", description = "删除生日ID: #id")
    public ApiResult<String> delete(@PathVariable String id) {
        return birthdayService.delete(id);
    }

    /**
     * 根据ID查询生日详情
     *
     * @param id 生日ID
     * @return 生日详情
     */
    @GetMapping("/{id}")
    @SaCheckPermission(value = "birthday:query", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<SysBirthdayPageVO> getById(@PathVariable String id) {
        return birthdayService.getById(id);
    }

    /**
     * 分页查询生日列表
     *
     * @param pageQuery 分页查询参数
     * @return 生日分页列表
     */
    @GetMapping("/page")
    @SaCheckPermission(value = "birthday:list", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<PageData<SysBirthdayPageVO>> list(SysBirthdayPageQuery pageQuery) {
        return birthdayService.list(pageQuery);
    }
}
