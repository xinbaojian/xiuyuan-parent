package xin.xiuyuan.common.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 通用下拉列表数据类
 *
 * @author xinbaojian
 * @create 2025-12-20 21:48
 **/
@Data
@Accessors(chain = true)
public class Option {

    /**
     * 显示的文本
     */
    private String label;

    /**
     * 存储的值
     */
    private String value;
}
