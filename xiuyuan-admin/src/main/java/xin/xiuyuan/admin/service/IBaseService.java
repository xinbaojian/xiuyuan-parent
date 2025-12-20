package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.entity.BaseEntity;
import xin.xiuyuan.admin.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 公共Service
 *
 * @author xinbaojian
 * @create 2025-12-20 11:43
 **/
public interface IBaseService<T extends BaseEntity> {

    /**
     * 获取用户 Map
     *
     * @param entityList 实体列表
     * @return 用户 Map
     */
    Map<String, SysUser> getUserMap(List<T> entityList);
}
