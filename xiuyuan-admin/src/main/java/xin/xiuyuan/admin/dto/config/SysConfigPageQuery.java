package xin.xiuyuan.admin.dto.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xin.xiuyuan.admin.dto.BasePageQuery;
import xin.xiuyuan.common.types.ConfigType;

/**
 * 系统配置分页查询 DTO
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigPageQuery extends BasePageQuery {

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 系统内置（Y是 N否）
     */
    private ConfigType configType;
}