package xin.xiuyuan.admin.dto.subscription;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.SubscriptionType;

import java.time.LocalDateTime;

/**
 * 订阅表单 DTO
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Data
@Accessors(chain = true)
public class SysSubscriptionForm {

    /**
     * 订阅名称
     */
    @NotBlank(message = "订阅名称不能为空")
    private String subscriptionName;

    /**
     * 订阅类型
     */
    @NotNull(message = "订阅类型不能为空")
    private SubscriptionType subscriptionType;

    /**
     * 提前提醒天数
     */
    @NotNull(message = "提前提醒天数不能为空")
    @Min(value = 1, message = "提前提醒天数至少为1天")
    private Integer advanceReminderDays;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endDate;

    /**
     * 状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 备注
     */
    private String remark;
}
