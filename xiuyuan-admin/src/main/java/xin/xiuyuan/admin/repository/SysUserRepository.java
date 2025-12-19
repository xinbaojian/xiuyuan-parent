package xin.xiuyuan.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xin.xiuyuan.admin.entity.SysUser;

/**
 * 用户 repository
 *
 * @author xinbaojian
 * @create 2025-12-15 17:32
 **/
@Repository
public interface SysUserRepository extends MongoRepository<SysUser, String> {

    /**
     * 根据登录账号查询用户
     *
     * @param loginName 登录账号
     * @return 用户信息
     */
    SysUser findByLoginName(String loginName);

    /**
     * 根据登录账号和排除指定ID查询用户
     *
     * @param loginName 登录账号
     * @param id        排除的用户ID
     * @return 用户信息
     */
    SysUser findByLoginNameAndIdNot(String loginName, String id);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    SysUser findByEmail(String email);

    /**
     * 根据邮箱和排除指定ID查询用户
     *
     * @param email 邮箱
     * @param id    排除的用户ID
     * @return 用户信息
     */
    SysUser findByEmailAndIdNot(String email, String id);

    /**
     * 根据手机号码查询用户
     *
     * @param mobile 手机号码
     * @return 用户信息
     */
    SysUser findByMobile(String mobile);

    /**
     * 根据手机号码和排除指定ID查询用户
     *
     * @param mobile 手机号码
     * @param id     排除的用户ID
     * @return 用户信息
     */
    SysUser findByMobileAndIdNot(String mobile, String id);
}