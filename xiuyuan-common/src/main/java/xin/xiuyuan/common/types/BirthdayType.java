package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 生日类型枚举
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Getter
@AllArgsConstructor
public enum BirthdayType {

    /**
     * 公历生日
     */
    SOLAR("solar", "公历生日"),

    /**
     * 农历生日
     */
    LUNAR("lunar", "农历生日");

    /**
     * 生日类型代码
     */
    private final String code;

    /**
     * 生日类型名称
     */
    private final String name;

    /**
     * 根据代码获取生日类型
     *
     * @param code 生日类型代码
     * @return 生日类型枚举
     */
    public static BirthdayType fromCode(String code) {
        for (BirthdayType type : values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("不支持的生日类型: " + code);
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
