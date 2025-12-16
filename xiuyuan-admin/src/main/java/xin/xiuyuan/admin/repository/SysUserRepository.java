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
}
