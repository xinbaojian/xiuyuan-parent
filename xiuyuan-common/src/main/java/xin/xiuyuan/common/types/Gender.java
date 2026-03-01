package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Getter
@AllArgsConstructor
public enum Gender {

    /**
     * 男
     */
    MALE("male", "男"),

    /**
     * 女
     */
    FEMALE("female", "女"),

    /**
     * 未知
     */
    UNKNOWN("unknown", "未知");

    /**
     * 性别代码
     */
    private final String code;

    /**
     * 性别名称
     */
    private final String name;

    /**
     * 根据代码获取性别
     *
     * @param code 性别代码
     * @return 性别枚举
     */
    public static Gender fromCode(String code) {
        for (Gender gender : values()) {
            if (gender.getCode().equalsIgnoreCase(code)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("不支持的性别: " + code);
    }

    /**
     * 获取描述信息
     *
     * @return 名称
     */
    public String getDesc() {
        return name;
    }
}
