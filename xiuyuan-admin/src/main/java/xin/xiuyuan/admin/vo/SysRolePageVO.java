package xin.xiuyuan.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import xin.xiuyuan.common.types.CommonStatus;

import java.time.LocalDateTime;

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
     * 备注
     */
    private String remark;

    public String getStatusDesc() {
        return status == null ? null : status.getDesc();
    }
}