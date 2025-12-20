package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.dto.config.SysConfigForm;
import xin.xiuyuan.admin.dto.config.SysConfigPageQuery;
import xin.xiuyuan.admin.entity.SysConfig;
import xin.xiuyuan.admin.vo.SysConfigPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

import java.util.Optional;

/**
 * 系统配置 Service
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
public interface ISysConfigService extends IBaseService<SysConfig> {

    /**
     * 保存系统配置
     *
     * @param form 系统配置表单
     * @return ApiResult
     */
    ApiResult<String> save(SysConfigForm form);

    /**
     * 更新系统配置
     *
     * @param id   系统配置ID
     * @param form 系统配置表单
     * @return ApiResult
     */
    ApiResult<String> update(String id, SysConfigForm form);

    /**
     * 删除系统配置
     *
     * @param id 系统配置 ID
     * @return ApiResult
     */
    ApiResult<String> delete(String id);

    /**
     * 分页查询系统配置列表
     *
     * @param pageQuery 分页查询参数
     * @return 系统配置分页列表
     */
    ApiResult<PageData<SysConfigPageVO>> list(SysConfigPageQuery pageQuery);

    /**
     * 根据 key 获取配置值
     *
     * @param key 配置 key
     * @return 配置值
     */
    Optional<String> getConfigValue(String key);
}