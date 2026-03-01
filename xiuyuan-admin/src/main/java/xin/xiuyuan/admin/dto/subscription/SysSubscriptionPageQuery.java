package xin.xiuyuan.admin.dto.subscription;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xin.xiuyuan.admin.dto.BasePageQuery;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.SubscriptionType;

import java.time.LocalDateTime;

/**
 * 订阅分页查询参数类
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSubscriptionPageQuery extends BasePageQuery {

    /**
     * 订阅名称
     */
    private String subscriptionName;

    /**
     * 订阅类型
     */
    private SubscriptionType subscriptionType;

    /**
     * 状态
     */
    private CommonStatus status;

    /**
     * 开始时间-查询条件
     */
    private LocalDateTime startDateStart;

    /**
     * 开始时间-查询条件
     */
    private LocalDateTime startDateEnd;

    /**
     * 结束时间-查询条件
     */
    private LocalDateTime endDateStart;

    /**
     * 结束时间-查询条件
     */
    private LocalDateTime endDateEnd;
}
