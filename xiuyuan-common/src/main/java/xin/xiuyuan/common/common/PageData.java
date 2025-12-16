package xin.xiuyuan.common.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author xinbaojian
 * @create 2025-12-16 12:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> implements Serializable {

    private List<T> list;
    private Long total;
}
