package xin.xiuyuan.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xin.xiuyuan.admin.entity.SysPost;

import java.util.List;

/**
 * 岗位 Repository
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Repository
public interface SysPostRepository extends MongoRepository<SysPost, String> {

    /**
     * 根据岗位编码查询岗位
     *
     * @param postCode 岗位编码
     * @return 岗位
     */
    SysPost findByPostCode(String postCode);

    /**
     * 根据岗位名称查询岗位
     *
     * @param postName 岗位名称
     * @return 岗位
     */
    SysPost findByPostName(String postName);

    /**
     * 根据岗位编码查询岗位（排除指定ID）
     *
     * @param postCode 岗位编码
     * @param id       岗位ID
     * @return 岗位
     */
    SysPost findByPostCodeAndIdNot(String postCode, String id);

    /**
     * 根据岗位名称查询岗位（排除指定ID）
     *
     * @param postName 岗位名称
     * @param id       岗位ID
     * @return 岗位
     */
    SysPost findByPostNameAndIdNot(String postName, String id);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<SysPost> findAll();
}