package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限范围枚举
 *
 * @author xinbaojian
 * @create 2025-02-22
 */
@Getter
@AllArgsConstructor
public enum DataScopeType {

    /**
     * 全部数据权限
     */
    ALL(1, "全部数据权限"),

    /**
     * 本部门数据权限
     */
    DEPT(2, "本部门数据权限"),

    /**
     * 本部门及子部门数据权限
     */
    DEPT_AND_CHILD(3, "本部门及子部门数据权限"),

    /**
     * 仅本人数据权限
     */
    SELF(4, "仅本人数据权限"),

    /**
     * 自定义数据权限
     */
    CUSTOM(5, "自定义数据权限");

    private final Integer code;
    private final String desc;

    public static DataScopeType fromCode(Integer code) {
        for (DataScopeType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return SELF;
    }
}
