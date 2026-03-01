package xin.xiuyuan.admin.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xin.xiuyuan.admin.entity.SysSubscription;
import xin.xiuyuan.admin.repository.SysSubscriptionRepository;
import xin.xiuyuan.admin.service.ISubscriptionNotificationService;
import xin.xiuyuan.common.types.CommonStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 订阅到期提醒定时任务
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionReminderTask {

    private final SysSubscriptionRepository subscriptionRepository;
    private final ISubscriptionNotificationService notificationService;

    /**
     * 订阅到期提醒检查
     * 每天凌晨0点执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkSubscriptionExpiry() {
        log.info("开始检查订阅到期提醒");

        try {
            // 查询所有启用状态的订阅
            List<SysSubscription> subscriptions = subscriptionRepository.findByStatus(CommonStatus.NORMAL);

            if (subscriptions.isEmpty()) {
                log.info("没有启用的订阅需要检查");
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            int reminderCount = 0;
            int disabledCount = 0;
            List<SysSubscription> toDisable = new ArrayList<>();

            for (SysSubscription subscription : subscriptions) {
                if (subscription.getEndDate() == null || subscription.getAdvanceReminderDays() == null) {
                    continue;
                }

                // 计算剩余天数
                long remainingDays = ChronoUnit.DAYS.between(now, subscription.getEndDate());

                // 如果剩余天数小于等于提前提醒天数,发送提醒
                if (remainingDays <= subscription.getAdvanceReminderDays()) {
                    reminderCount++;
                    notificationService.sendReminder(subscription, remainingDays);

                    // 如果剩余天数为0(已到期),发送完提醒后标记为待禁用
                    if (remainingDays == 0) {
                        toDisable.add(subscription);
                        disabledCount++;
                    }
                }
            }

            // 批量禁用已到期的订阅
            if (!toDisable.isEmpty()) {
                for (SysSubscription subscription : toDisable) {
                    subscription.setStatus(CommonStatus.DISABLE);
                    subscriptionRepository.save(subscription);
                    log.info("订阅已到期并自动禁用: [{}], 结束时间: [{}]",
                            subscription.getSubscriptionName(), subscription.getEndDate());
                }
            }

            log.info("订阅到期检查完成, 共检查 {} 个订阅, 触发提醒 {} 个, 自动禁用 {} 个",
                    subscriptions.size(), reminderCount, disabledCount);

        } catch (Exception e) {
            log.error("订阅到期检查任务执行失败", e);
        }
    }
}
