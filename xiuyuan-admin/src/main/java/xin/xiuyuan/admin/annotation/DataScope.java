package xin.xiuyuan.admin.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 *
 * @author xinbaojian
 * @create 2025-02-22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 部门表别名
     */
    String deptAlias() default "d";

    /**
     * 用户表别名
     */
    String userAlias() default "u";

    /**
     * 部门ID字段名
     */
    String deptIdField() default "deptId";

    /**
     * 用户ID字段名
     */
    String userIdField() default "userId";
}
