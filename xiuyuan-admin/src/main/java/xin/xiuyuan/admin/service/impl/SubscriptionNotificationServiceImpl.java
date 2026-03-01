package xin.xiuyuan.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xin.xiuyuan.admin.entity.SysSubscription;
import xin.xiuyuan.admin.service.ISubscriptionNotificationService;

/**
 * 订阅到期通知服务实现(默认实现,仅打印日志)
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionNotificationServiceImpl implements ISubscriptionNotificationService {

    @Override
    public void sendReminder(SysSubscription subscription, long remainingDays) {
        log.warn("订阅到期提醒: [{}] 订阅类型: [{}], 剩余天数: [{}]天, 结束时间: [{}], 备注: [{}]",
                subscription.getSubscriptionName(),
                subscription.getSubscriptionType().getDesc(),
                remainingDays,
                subscription.getEndDate(),
                subscription.getRemark());
    }
}
