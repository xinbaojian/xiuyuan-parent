package xin.xiuyuan.admin.vo.subscription;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.SubscriptionType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订阅分页查询VO
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Data
public class SysSubscriptionPageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    private String id;

    /**
     * 订阅名称
     */
    private String subscriptionName;

    /**
     * 订阅类型
     */
    private SubscriptionType subscriptionType;
    /**
     * 提前提醒天数
     */
    private Integer advanceReminderDays;
    /**
     * 剩余天数(自动计算)
     */
    private Integer remainingDays;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startDate;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endDate;
    /**
     * 状态
     */
    private CommonStatus status;
    /**
     * 描述
     */
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 订阅类型描述
     */
    public String getSubscriptionTypeDesc() {
        return subscriptionType == null ? null : subscriptionType.getDesc();
    }

    /**
     * 状态描述
     */
    public String getStatusDesc() {
        return status == null ? null : status.getDesc();
    }
}
