package xin.xiuyuan.admin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xin.xiuyuan.common.types.CommonStatus;

/**
 * 角色分页查询对象
 *
 * @author xinbaojian
 * @date 2025-12-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRolePageQuery extends BasePageQuery {

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 状态
     */
    private CommonStatus status;
}