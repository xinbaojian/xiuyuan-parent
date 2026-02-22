package xin.xiuyuan.admin.vo.operationlog;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import xin.xiuyuan.common.types.OperationStatus;

import java.time.LocalDateTime;

/**
 * 操作日志分页视图对象
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
@Data
public class SysOperationLogPageVO {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 操作部门名称
     */
    private String deptName;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求IP
     */
    private String operIp;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime operTime;

    /**
     * 操作状态
     */
    private OperationStatus status;

    /**
     * 执行时长(毫秒)
     */
    private Integer costTime;

    /**
     * 操作状态描述
     */
    public String getStatusDesc() {
        return status == null ? null : status.getDesc();
    }
}
