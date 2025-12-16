package xin.xiuyuan.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xin.xiuyuan.admin.entity.SysDept;

import java.util.List;

/**
 * 部门 Repository
 *
 * @author xinbaojian
 * @create 2025-12-15 18:01
 **/
@Repository
public interface SysDeptRepository extends MongoRepository<SysDept, String> {

    /**
     * 根据部门名称查询部门
     *
     * @param deptName 部门名称
     * @return 部门
     */
    SysDept findByDeptName(String deptName);

    /**
     * 查询所有部门
     *
     * @return 部门列表
     */
    List<SysDept> findAllByDelFlagIsFalse();
}