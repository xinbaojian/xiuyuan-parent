package xin.xiuyuan.admin.dto.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.ConfigType;

/**
 * 系统配置表单 DTO
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Data
@Accessors(chain = true)
public class SysConfigForm {

    /**
     * 参数名称
     */
    @NotBlank(message = "参数名称不能为空")
    private String configName;

    /**
     * 参数键名
     */
    @NotBlank(message = "参数键名不能为空")
    private String configKey;

    /**
     * 参数键值
     */
    @NotBlank(message = "参数键值不能为空")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    private ConfigType configType = ConfigType.Y;

    /**
     * 备注
     */
    private String remark;
}