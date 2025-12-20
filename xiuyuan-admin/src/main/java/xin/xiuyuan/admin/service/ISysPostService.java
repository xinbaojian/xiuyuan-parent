package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.dto.post.SysPostForm;
import xin.xiuyuan.admin.dto.post.SysPostPageQuery;
import xin.xiuyuan.admin.vo.post.SysPostPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.Option;
import xin.xiuyuan.common.common.PageData;

import java.util.List;

/**
 * 岗位 Service
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
public interface ISysPostService {

    /**
     * 保存岗位
     *
     * @param form 岗位表单
     * @return ApiResult
     */
    ApiResult<String> save(SysPostForm form);

    /**
     * 更新岗位
     *
     * @param id   岗位ID
     * @param form 岗位表单
     * @return ApiResult
     */
    ApiResult<String> update(String id, SysPostForm form);

    /**
     * 删除岗位
     *
     * @param id 岗位 ID
     * @return ApiResult
     */
    ApiResult<String> delete(String id);

    /**
     * 分页查询岗位列表
     *
     * @param pageQuery 分页查询参数
     * @return 岗位分页列表
     */
    ApiResult<PageData<SysPostPageVO>> list(SysPostPageQuery pageQuery);

    /**
     * 获取岗位下拉列表
     *
     * @return 岗位下拉列表
     */
    ApiResult<List<Option>> options();
}