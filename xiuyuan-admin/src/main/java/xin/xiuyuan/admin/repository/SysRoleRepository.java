package xin.xiuyuan.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xin.xiuyuan.admin.entity.SysRole;

/**
 * 角色数据访问层
 *
 * @author xinbaojian
 * @date 2025-12-17
 */
@Repository
public interface SysRoleRepository extends MongoRepository<SysRole, String> {
    /**
     * 根据角色权限字符串查询角色
     *
     * @param roleKey 角色权限字符串
     * @return 角色信息
     */
    SysRole findByRoleKey(String roleKey);

    /**
     * 根据角色权限字符串和排除指定ID查询角色
     *
     * @param roleKey 角色权限字符串
     * @param id      排除的角色ID
     * @return 角色信息
     */
    SysRole findByRoleKeyAndIdNot(String roleKey, String id);

    /**
     * 根据角色名称查询角色
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    SysRole findByRoleName(String roleName);

    /**
     * 根据角色名称和排除指定ID查询角色
     *
     * @param roleName 角色名称
     * @param id       排除的角色ID
     * @return 角色信息
     */
    SysRole findByRoleNameAndIdNot(String roleName, String id);
}