package xin.xiuyuan.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.CommonStatus;

/**
 * 角色表单对象
 *
 * @author xinbaojian
 * @date 2025-12-17
 */
@Data
@Accessors(chain = true)
public class SysRoleForm {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色权限字符串(唯一)
     */
    @NotBlank(message = "角色权限字符串不能为空")
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer orderNum = 0;

    /**
     * 角色状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 备注
     */
    private String remark;
}