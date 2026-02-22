package xin.xiuyuan.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.DataScopeType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色分页视图对象
 *
 * @author xinbaojian
 * @date 2025-12-17
 */
@Data
public class SysRolePageVO {

    /**
     * 角色ID
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 角色状态
     */
    private CommonStatus status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    private String createBy;
    private String createByName;

    /**
     * 更新者
     */
    private String updateBy;
    private String updateByName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据范围
     */
    private DataScopeType dataScope;

    /**
     * 数据范围描述
     */
    private String dataScopeDesc;

    /**
     * 自定义部门ID列表
     */
    private List<String> customDeptIds;

    public String getStatusDesc() {
        return status == null ? null : status.getDesc();
    }

    public String getDataScopeDesc() {
        return dataScope == null ? null : dataScope.getDesc();
    }
}