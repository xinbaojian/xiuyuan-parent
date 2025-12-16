package xin.xiuyuan.admin.service;

import cn.hutool.core.lang.tree.Tree;
import xin.xiuyuan.admin.dto.dept.SysDeptForm;
import xin.xiuyuan.admin.dto.dept.SysDeptPageQuery;
import xin.xiuyuan.admin.vo.dept.SysDeptPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.types.CommonStatus;

import java.util.List;

/**
 * 部门 Service
 *
 * @author xinbaojian
 * @create 2025-12-15 18:02
 **/
public interface ISysDeptService {

    /**
     * 保存部门
     *
     * @param form 部门表单
     * @return ApiResult
     */
    ApiResult<String> save(SysDeptForm form);

    /**
     * 更新部门
     *
     * @param form 部门表单
     * @return ApiResult
     */
    ApiResult<String> update(String id, SysDeptForm form);

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return ApiResult
     */
    ApiResult<String> delete(String id);

    /**
     * 分页查询部门列表
     *
     * @param pageQuery 分页查询参数
     * @return 部门分页列表
     */
    ApiResult<PageData<SysDeptPageVO>> list(SysDeptPageQuery pageQuery);

    /**
     * 获取部门树
     *
     * @param deptName 部门名称
     * @param status   部门状态
     * @return 部门树
     */
    ApiResult<List<Tree<String>>> getDeptTree(String deptName, CommonStatus status);
}