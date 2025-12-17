package xin.xiuyuan.admin.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.CommonStatus;

/**
 * 岗位表单 DTO
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Data
@Accessors(chain = true)
public class SysPostForm {

    /**
     * 岗位编码
     */
    @NotBlank(message = "岗位编码不能为空")
    private String postCode;

    /**
     * 岗位名称
     */
    @NotBlank(message = "岗位名称不能为空")
    private String postName;

    /**
     * 显示顺序
     */
    private Integer orderNum = 0;

    /**
     * 岗位状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 备注
     */
    private String remark;
}