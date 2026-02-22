package xin.xiuyuan.admin.dto.operationlog;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import xin.xiuyuan.admin.dto.BasePageQuery;
import xin.xiuyuan.common.types.OperationStatus;

import java.time.LocalDateTime;

/**
 * 操作日志分页查询对象
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperationLogPageQuery extends BasePageQuery {

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作人ID
     */
    private String operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 操作状态
     */
    private OperationStatus status;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
