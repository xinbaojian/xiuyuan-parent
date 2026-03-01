package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.entity.SysSubscription;

/**
 * 订阅到期通知服务接口
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
public interface ISubscriptionNotificationService {

    /**
     * 发送订阅到期提醒
     *
     * @param subscription  订阅信息
     * @param remainingDays 剩余天数
     */
    void sendReminder(SysSubscription subscription, long remainingDays);
}
