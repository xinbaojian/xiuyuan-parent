package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录设备枚举
 *
 * @author xinbaojian
 * @create 2025-12-19 11:38
 **/
@Getter
@AllArgsConstructor
public enum LoginDevice {

    /**
     * PC
     */
    PC("PC"),

    /**
     * 手机
     */
    MOBILE("MOBILE");

    private final String desc;
}
