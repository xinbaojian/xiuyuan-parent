package xin.xiuyuan.admin.dto.dept;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xin.xiuyuan.admin.dto.BasePageQuery;
import xin.xiuyuan.common.types.CommonStatus;

/**
 * 分页查询参数类
 *
 * @author xinbaojian
 * @create 2025-12-16 12:09
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptPageQuery extends BasePageQuery {

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门状态
     */
    private CommonStatus status;
}
