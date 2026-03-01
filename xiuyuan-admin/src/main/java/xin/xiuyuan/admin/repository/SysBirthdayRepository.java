package xin.xiuyuan.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xin.xiuyuan.admin.entity.SysBirthday;
import xin.xiuyuan.common.types.CommonStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * 生日管理 Repository
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Repository
public interface SysBirthdayRepository extends MongoRepository<SysBirthday, String> {

    /**
     * 根据姓名查询生日
     *
     * @param name 姓名
     * @return 生日
     */
    SysBirthday findByName(String name);

    /**
     * 根据姓名查询生日(排除指定ID)
     *
     * @param name 姓名
     * @param id   生日ID
     * @return 生日
     */
    SysBirthday findByNameAndIdNot(String name, String id);

    /**
     * 查询所有启用状态的生日
     *
     * @param status 状态
     * @return 启用状态的生日列表
     */
    List<SysBirthday> findByStatus(CommonStatus status);

    /**
     * 查询指定日期范围内需要提醒的生日
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param status    状态
     * @return 生日列表
     */
    List<SysBirthday> findByNextBirthdayBetweenAndStatus(LocalDate startDate, LocalDate endDate, CommonStatus status);
}
