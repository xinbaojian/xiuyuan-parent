package xin.xiuyuan.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.xiuyuan.admin.annotation.OperationLog;
import xin.xiuyuan.admin.dto.config.SysConfigForm;
import xin.xiuyuan.admin.dto.config.SysConfigPageQuery;
import xin.xiuyuan.admin.service.ISysConfigService;
import xin.xiuyuan.admin.vo.SysConfigPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

/**
 * 系统设置/系统配置
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@RestController
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class SysConfigController {

    private final ISysConfigService configService;


    /**
     * 新增系统配置
     *
     * @param form 系统配置表单
     * @return 新增结果
     */
    @PostMapping
    @SaCheckPermission("setting:config:add")
    @OperationLog(module = "系统配置", operationType = "新增", description = "新增系统配置")
    public ApiResult<String> save(@RequestBody @Validated SysConfigForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return configService.save(form);
    }

    /**
     * 修改系统配置
     *
     * @param id   系统配置 ID
     * @param form 系统配置表单
     * @return 修改结果
     */
    @PostMapping("/{id}")
    @SaCheckPermission("setting:config:edit")
    @OperationLog(module = "系统配置", operationType = "修改", description = "修改系统配置")
    public ApiResult<String> update(@PathVariable String id, @RequestBody @Validated SysConfigForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return configService.update(id, form);
    }

    /**
     * 删除系统配置
     *
     * @param id 系统配置 ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission("setting:config:delete")
    @OperationLog(module = "系统配置", operationType = "删除", description = "删除系统配置ID: #id")
    public ApiResult<String> delete(@PathVariable String id) {
        return configService.delete(id);
    }

    /**
     * 分页查询系统配置列表
     *
     * @param pageQuery 分页查询参数
     * @return 系统配置分页列表
     */
    @GetMapping("/page")
    @SaCheckPermission("setting:config:list")
    public ApiResult<PageData<SysConfigPageVO>> list(SysConfigPageQuery pageQuery) {
        return configService.list(pageQuery);
    }
}