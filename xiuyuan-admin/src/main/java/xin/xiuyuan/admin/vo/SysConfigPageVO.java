package xin.xiuyuan.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.ConfigType;

import java.time.LocalDateTime;

/**
 * 系统配置页面 VO
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Data
@Accessors(chain = true)
public class SysConfigPageVO {

    /**
     * 主键 ID
     */
    private String id;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    private ConfigType configType;

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

    public String getConfigTypeDesc() {
        return configType == null ? null : configType.getDesc();
    }
}