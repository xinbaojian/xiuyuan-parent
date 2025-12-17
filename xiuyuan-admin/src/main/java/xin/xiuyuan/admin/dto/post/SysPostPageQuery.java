package xin.xiuyuan.admin.dto.post;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xin.xiuyuan.admin.dto.BasePageQuery;
import xin.xiuyuan.common.types.CommonStatus;

/**
 * 岗位分页查询参数类
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPostPageQuery extends BasePageQuery {

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 岗位状态
     */
    private CommonStatus status;
}