package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户性别
 *
 * @author xinbaojian
 * @create 2025-12-15 17:20
 **/
@Getter
@AllArgsConstructor
public enum UserSex {

    MALE("男"),

    FEMALE("女"),

    UNKNOWN("未知");;

    private final String desc;
}
