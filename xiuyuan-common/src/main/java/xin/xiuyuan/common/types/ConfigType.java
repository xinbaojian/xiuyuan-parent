package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 配置类型
 *
 * @author xinbaojian
 * @create 2025-12-17 09:32
 **/
@Getter
@AllArgsConstructor
public enum ConfigType {

    Y("是"),
    N("否");

    private final String desc;
}
