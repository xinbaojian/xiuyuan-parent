package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.dto.subscription.SysSubscriptionForm;
import xin.xiuyuan.admin.dto.subscription.SysSubscriptionPageQuery;
import xin.xiuyuan.admin.vo.subscription.SysSubscriptionPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

/**
 * 订阅管理 Service
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
public interface ISysSubscriptionService {

    /**
     * 保存订阅
     *
     * @param form 订阅表单
     * @return ApiResult
     */
    ApiResult<String> save(SysSubscriptionForm form);

    /**
     * 更新订阅
     *
     * @param id   订阅ID
     * @param form 订阅表单
     * @return ApiResult
     */
    ApiResult<String> update(String id, SysSubscriptionForm form);

    /**
     * 删除订阅
     *
     * @param id 订阅 ID
     * @return ApiResult
     */
    ApiResult<String> delete(String id);

    /**
     * 根据ID查询订阅详情
     *
     * @param id 订阅ID
     * @return ApiResult
     */
    ApiResult<SysSubscriptionPageVO> getById(String id);

    /**
     * 分页查询订阅列表
     *
     * @param pageQuery 分页查询参数
     * @return 订阅分页列表
     */
    ApiResult<PageData<SysSubscriptionPageVO>> list(SysSubscriptionPageQuery pageQuery);
}
