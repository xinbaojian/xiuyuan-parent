package xin.xiuyuan.admin.vo.dept;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import xin.xiuyuan.common.types.CommonStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门分页查询VO
 *
 * @author xinbaojian
 * @create 2025-12-16 12:23
 **/
@Data
public class SysDeptPageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    private String id;

    /**
     * 父部门 id
     */
    private String parentId = "00";

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum = 0;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 状态描述
     */
    public String getStatusDesc() {
        return status == null ? null : status.getDesc();
    }

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime = LocalDateTime.now();

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 备注
     */
    private String remark;

}