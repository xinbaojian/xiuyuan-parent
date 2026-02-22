package xin.xiuyuan.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作状态枚举
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
@Getter
@AllArgsConstructor
public enum OperationStatus {

    /**
     * 成功
     */
    SUCCESS("成功"),

    /**
     * 失败
     */
    FAILURE("失败");

    private final String desc;
}
