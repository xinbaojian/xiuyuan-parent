package xin.xiuyuan.admin.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xin.xiuyuan.admin.service.ISysOperationLogService;

/**
 * 操作日志定时清理任务
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class OperationLogCleanupTask {

    private final ISysOperationLogService operationLogService;

    /**
     * 清理过期操作日志
     * 每天凌晨0点执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredLogs() {
        log.info("开始清理过期操作日志");
        operationLogService.cleanExpiredLogs();
        log.info("完成清理过期操作日志");
    }
}
