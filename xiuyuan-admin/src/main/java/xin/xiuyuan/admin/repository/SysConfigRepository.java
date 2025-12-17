package xin.xiuyuan.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xin.xiuyuan.admin.entity.SysConfig;

/**
 * 系统配置 Repository
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Repository
public interface SysConfigRepository extends MongoRepository<SysConfig, String> {

    /**
     * 根据参数键名查询系统配置
     *
     * @param configKey 参数键名
     * @return 系统配置
     */
    SysConfig findByConfigKey(String configKey);

    /**
     * 根据参数键名查询系统配置（排除指定ID）
     *
     * @param configKey 参数键名
     * @param id        配置ID
     * @return 系统配置
     */
    SysConfig findByConfigKeyAndIdNot(String configKey, String id);
}