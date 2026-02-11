package xin.xiuyuan.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xin.xiuyuan.admin.entity.SysMenuPermission;

import java.util.List;

/**
 * 菜单权限 Repository
 *
 * @author xinbaojian
 * @create 2025-12-29 11:32
 **/
@Repository
public interface MenuPermissionRepository extends MongoRepository<SysMenuPermission, String> {

    /**
     * 根据路由名称查询菜单
     *
     * @param name 路由名称
     * @return 菜单权限
     */
    SysMenuPermission findByName(String name);

    /**
     * 查询所有未删除的菜单
     *
     * @return 菜单列表
     */
    List<SysMenuPermission> findAllByDelFlagIsFalse();

    /**
     * 根据父级 ID 查询是否存在菜单
     *
     * @param parentId 父级 ID
     * @return 是否存在
     */
    Boolean existsByParentId(String parentId);

    /**
     * 根据父级 ID 和名称查询菜单（排除指定 ID）
     *
     * @param parentId  父级 ID
     * @param name      路由名称
     * @param excludeId 需要排除的菜单 ID
     * @return 菜单权限
     */
    SysMenuPermission findByParentIdAndNameAndIdNot(String parentId, String name, String excludeId);

    /**
     * 根据父级 ID 和名称查询菜单
     *
     * @param parentId 父级 ID
     * @param name     路由名称
     * @return 菜单权限
     */
    SysMenuPermission findByParentIdAndName(String parentId, String name);
}
