package xin.xiuyuan.admin.dto.dept;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.CommonStatus;

/**
 * 新增部门表单 DTO
 *
 * @author xinbaojian
 * @create 2025-12-16 09:31
 **/
@Data
@Accessors(chain = true)
public class SysDeptForm {

    /**
     * 父部门 id
     */
    private String parentId = "00";

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
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
}
