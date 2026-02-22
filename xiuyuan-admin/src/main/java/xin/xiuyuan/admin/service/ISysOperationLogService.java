package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.dto.operationlog.SysOperationLogPageQuery;
import xin.xiuyuan.admin.entity.SysOperationLog;
import xin.xiuyuan.admin.vo.operationlog.SysOperationLogPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

/**
 * 操作日志 Service
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
public interface ISysOperationLogService extends IBaseService<SysOperationLog> {

    /**
     * 分页查询操作日志
     *
     * @param pageQuery 分页查询参数
     * @return 操作日志分页列表
     */
    ApiResult<PageData<SysOperationLogPageVO>> list(SysOperationLogPageQuery pageQuery);

    /**
     * 根据ID查询操作日志详情
     *
     * @param id 日志ID
     * @return 日志详情
     */
    ApiResult<SysOperationLogPageVO> findById(String id);

    /**
     * 清理过期的操作日志
     */
    void cleanExpiredLogs();
}
