package xin.xiuyuan.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.annotation.OperationLog;
import xin.xiuyuan.admin.dto.subscription.SysSubscriptionForm;
import xin.xiuyuan.admin.dto.subscription.SysSubscriptionPageQuery;
import xin.xiuyuan.admin.service.ISysSubscriptionService;
import xin.xiuyuan.admin.vo.subscription.SysSubscriptionPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.constant.RoleConstant;

/**
 * 订阅管理
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@RestController
@RequestMapping("/admin/subscription")
@RequiredArgsConstructor
public class SysSubscriptionController {

    private final ISysSubscriptionService subscriptionService;

    /**
     * 新增订阅
     *
     * @param form 订阅表单
     * @return 新增结果
     */
    @PostMapping
    @SaCheckPermission(value = "subscription:add", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    @OperationLog(module = "订阅管理", operationType = "新增", description = "新增订阅")
    public ApiResult<String> save(@RequestBody @Validated SysSubscriptionForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return subscriptionService.save(form);
    }

    /**
     * 修改订阅
     *
     * @param id   订阅 ID
     * @param form 订阅表单
     * @return 修改结果
     */
    @PutMapping("/{id}")
    @SaCheckPermission(value = "subscription:edit", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    @OperationLog(module = "订阅管理", operationType = "修改", description = "修改订阅ID: #id")
    public ApiResult<String> update(@PathVariable String id, @RequestBody @Validated SysSubscriptionForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return subscriptionService.update(id, form);
    }

    /**
     * 删除订阅
     *
     * @param id 订阅 ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission(value = "subscription:delete", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    @OperationLog(module = "订阅管理", operationType = "删除", description = "删除订阅ID: #id")
    public ApiResult<String> delete(@PathVariable String id) {
        return subscriptionService.delete(id);
    }

    /**
     * 根据ID查询订阅详情
     *
     * @param id 订阅ID
     * @return 订阅详情
     */
    @GetMapping("/{id}")
    @SaCheckPermission(value = "subscription:query", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<SysSubscriptionPageVO> getById(@PathVariable String id) {
        return subscriptionService.getById(id);
    }

    /**
     * 分页查询订阅列表
     *
     * @param pageQuery 分页查询参数
     * @return 订阅分页列表
     */
    @GetMapping("/page")
    @SaCheckPermission(value = "subscription:list", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<PageData<SysSubscriptionPageVO>> list(SysSubscriptionPageQuery pageQuery) {
        return subscriptionService.list(pageQuery);
    }
}
