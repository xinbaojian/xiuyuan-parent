package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型
 *
 * @author xinbaojian
 * @create 2025-12-29 15:04
 **/
@Getter
@AllArgsConstructor
public enum MenuType {

    MENU("MENU", "菜单"),

    BUTTON("BUTTON", "按钮");

    private final String type;

    private final String desc;

    public static MenuType getByType(String type) {
        for (MenuType value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
