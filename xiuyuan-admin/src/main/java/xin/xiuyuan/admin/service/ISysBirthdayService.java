package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.dto.birthday.SysBirthdayForm;
import xin.xiuyuan.admin.dto.birthday.SysBirthdayPageQuery;
import xin.xiuyuan.admin.vo.birthday.SysBirthdayPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

/**
 * 生日管理 Service
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
public interface ISysBirthdayService {

    /**
     * 保存生日
     *
     * @param form 生日表单
     * @return ApiResult
     */
    ApiResult<String> save(SysBirthdayForm form);

    /**
     * 更新生日
     *
     * @param id   生日ID
     * @param form 生日表单
     * @return ApiResult
     */
    ApiResult<String> update(String id, SysBirthdayForm form);

    /**
     * 删除生日
     *
     * @param id 生日 ID
     * @return ApiResult
     */
    ApiResult<String> delete(String id);

    /**
     * 根据ID查询生日详情
     *
     * @param id 生日ID
     * @return ApiResult
     */
    ApiResult<SysBirthdayPageVO> getById(String id);

    /**
     * 分页查询生日列表
     *
     * @param pageQuery 分页查询参数
     * @return 生日分页列表
     */
    ApiResult<PageData<SysBirthdayPageVO>> list(SysBirthdayPageQuery pageQuery);
}
