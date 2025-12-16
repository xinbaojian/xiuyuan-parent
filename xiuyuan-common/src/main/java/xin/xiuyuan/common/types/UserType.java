package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @author xinbaojian
 * @create 2025-12-15 17:18
 **/
@Getter
@AllArgsConstructor
public enum UserType {

    /**
     * 系统用户
     */
    SYSTEM_USER("system_user", "系统用户"),

    /**
     * 普通用户
     */
    NORMAL_USER("normal_user", "普通用户");

    private final String name;

    private final String desc;
}
