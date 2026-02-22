package xin.xiuyuan.admin.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.DataScopeType;

import java.util.List;

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

    /**
     * 数据范围（1-5：全部/本部门/本部门及子部门/仅本人/自定义）
     */
    private DataScopeType dataScope = DataScopeType.SELF;

    /**
     * 自定义部门ID列表（当 dataScope = CUSTOM 时使用）
     */
    private List<String> customDeptIds;
}