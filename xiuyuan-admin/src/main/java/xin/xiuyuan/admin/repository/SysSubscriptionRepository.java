package xin.xiuyuan.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xin.xiuyuan.admin.entity.SysSubscription;
import xin.xiuyuan.common.types.CommonStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订阅管理 Repository
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Repository
public interface SysSubscriptionRepository extends MongoRepository<SysSubscription, String> {

    /**
     * 根据订阅名称查询订阅
     *
     * @param subscriptionName 订阅名称
     * @return 订阅
     */
    SysSubscription findBySubscriptionName(String subscriptionName);

    /**
     * 根据订阅名称查询订阅(排除指定ID)
     *
     * @param subscriptionName 订阅名称
     * @param id               订阅ID
     * @return 订阅
     */
    SysSubscription findBySubscriptionNameAndIdNot(String subscriptionName, String id);

    /**
     * 查询所有启用状态的订阅
     *
     * @param status 状态
     * @return 启用状态的订阅列表
     */
    List<SysSubscription> findByStatus(CommonStatus status);

    /**
     * 查询指定时间范围内到期的订阅(用于定时任务)
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param status    状态
     * @return 订阅列表
     */
    List<SysSubscription> findByEndDateBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate, CommonStatus status);
}
