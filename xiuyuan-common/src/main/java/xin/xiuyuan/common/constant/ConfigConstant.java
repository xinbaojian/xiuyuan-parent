package xin.xiuyuan.common.constant;

/**
 * 系统配置常量
 *
 * @author xinbaojian
 * @create 2025-12-17 09:46
 **/
public interface ConfigConstant {

    /**
     * 初始化密码
     */
    String SYS_USER_INIT_PASSWORD_KEY = "sys.user.initPassword";

    /**
     * 操作日志保留时间(天)
     * <=0 表示永久保留
     */
    String SYS_OPERATION_LOG_RETENTION_DAYS = "sys.operation.log.retentionDays";
}
