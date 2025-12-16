package xin.xiuyuan.admin.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 分页查询基础类
 *
 * @author xinbaojian
 * @create 2025-12-16 12:09
 **/
@Setter
@Accessors(chain = true)
public class BasePageQuery {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    public Integer getPageNo() {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = Integer.max(pageNo, 1);
    }

    public Integer getPageSize() {
        if (pageSize < 1) {
            pageSize = 10;
        }
        return pageSize;
    }

    public Integer getPage() {
        return this.pageNo != null && this.pageNo > 1 ? this.pageNo - 1 : 0;
    }

    public Integer getOffset() {
        return (this.pageNo - 1) * this.pageSize;
    }
}
