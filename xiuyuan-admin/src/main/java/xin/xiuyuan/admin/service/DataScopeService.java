package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.context.DataScopeContext;

/**
 * 数据权限服务接口
 *
 * @author xinbaojian
 * @create 2025-02-22
 */
public interface DataScopeService {

    /**
     * 获取用户的数据权限上下文
     *
     * @param userId 用户ID
     * @return 数据权限上下文
     */
    DataScopeContext getDataScopeContext(String userId);

    /**
     * 清理用户数据权限缓存
     */
    void clearDataScopeCache();
}
