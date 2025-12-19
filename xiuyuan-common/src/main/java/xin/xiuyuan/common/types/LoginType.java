package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录方式枚举
 *
 * @author xinbaojian
 * @create 2025-12-19 11:37
 **/
@Getter
@AllArgsConstructor
public enum LoginType {

    /**
     * 账号密码登录
     */
    PASSWORD("password", "账号密码登录"),

    /**
     * 短信验证码登录
     */
    SMS("sms", "短信验证码登录"),

    /**
     * 扫码登录
     */
    QRCODE("qrcode", "扫码登录"),

    /**
     * 微信登录
     */
    WECHAT("wechat", "微信登录"),

    ;


    private final String type;

    private final String desc;
}
