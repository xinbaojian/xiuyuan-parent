package xin.xiuyuan.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.xiuyuan.admin.dto.operationlog.SysOperationLogPageQuery;
import xin.xiuyuan.admin.service.ISysOperationLogService;
import xin.xiuyuan.admin.vo.operationlog.SysOperationLogPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.constant.RoleConstant;

/**
 * 操作日志/操作日志
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
@RestController
@RequestMapping("/admin/operation/log")
@RequiredArgsConstructor
public class SysOperationLogController {

    private final ISysOperationLogService operationLogService;

    /**
     * 分页查询操作日志
     *
     * @param pageQuery 分页查询参数
     * @return 操作日志分页列表
     */
    @GetMapping("/page")
    @SaCheckPermission(value = "monitor:operation:log:list", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<PageData<SysOperationLogPageVO>> list(SysOperationLogPageQuery pageQuery) {
        return operationLogService.list(pageQuery);
    }

    /**
     * 查询操作日志详情
     *
     * @param id 日志ID
     * @return 日志详情
     */
    @GetMapping("/{id}")
    @SaCheckPermission(value = "monitor:operation:log:list", orRole = {RoleConstant.ROLE_ADMIN}, mode = SaMode.OR)
    public ApiResult<SysOperationLogPageVO> findById(@PathVariable String id) {
        return operationLogService.findById(id);
    }
}
