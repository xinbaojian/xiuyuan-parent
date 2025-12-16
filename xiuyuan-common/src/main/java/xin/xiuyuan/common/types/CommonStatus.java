package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态枚举
 *
 * @author xinbaojian
 * @create 2025-12-15 17:23
 **/
@Getter
@AllArgsConstructor
public enum CommonStatus {

    NORMAL("正常"),
    DISABLE("禁用");

    private final String desc;
}
