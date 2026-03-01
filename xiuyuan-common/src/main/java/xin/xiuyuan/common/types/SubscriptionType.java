package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订阅类型枚举
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Getter
@AllArgsConstructor
public enum SubscriptionType {

    /**
     * 软件订阅
     */
    SOFTWARE("software", "软件订阅"),

    /**
     * 服务订阅
     */
    SERVICE("service", "服务订阅"),

    /**
     * 会员订阅
     */
    MEMBERSHIP("membership", "会员订阅"),

    /**
     * 域名订阅
     */
    DOMAIN("domain", "域名订阅"),

    /**
     * 服务器订阅
     */
    SERVER("server", "服务器订阅"),

    /**
     * 其他订阅
     */
    OTHER("other", "其他订阅");

    /**
     * 订阅类型代码
     */
    private final String code;

    /**
     * 订阅类型名称
     */
    private final String name;

    /**
     * 根据代码获取订阅类型
     *
     * @param code 订阅类型代码
     * @return 订阅类型枚举
     */
    public static SubscriptionType fromCode(String code) {
        for (SubscriptionType type : values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("不支持的订阅类型: " + code);
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
