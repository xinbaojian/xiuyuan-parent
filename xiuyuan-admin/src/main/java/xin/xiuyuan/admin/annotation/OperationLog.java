package xin.xiuyuan.admin.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author xinbaojian
 * @create 2025-02-22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /**
     * 模块名称
     * 例如: "用户管理"、"部门管理"、"系统配置"
     */
    String module() default "";

    /**
     * 操作类型
     * 例如: "新增"、"修改"、"删除"、"查询"、"导入"、"导出"
     */
    String operationType() default "";

    /**
     * 操作描述
     * 支持SpEL表达式,例如: "新增用户: #form.username"
     * 如果不指定,自动生成: "{operationType} {module}"
     */
    String description() default "";

    /**
     * 是否记录请求参数
     */
    boolean recordParams() default true;

    /**
     * 是否记录返回结果
     */
    boolean recordResult() default true;

    /**
     * 敏感字段列表(不记录或脱敏)
     * 例如: {"password", "oldPassword", "newPassword", "salt"}
     */
    String[] sensitiveFields() default {"password", "pwd", "salt", "newPassword", "oldPassword", "mobile", "phone", "idCard", "identity"};
}
