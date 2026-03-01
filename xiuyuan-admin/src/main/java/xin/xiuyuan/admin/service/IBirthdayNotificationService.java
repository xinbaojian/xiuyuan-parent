package xin.xiuyuan.admin.service;

import xin.xiuyuan.admin.entity.SysBirthday;

/**
 * 生日提醒通知服务接口
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
public interface IBirthdayNotificationService {

    /**
     * 发送生日提醒
     *
     * @param birthday          生日信息
     * @param daysUntilBirthday 距离生日天数
     */
    void sendReminder(SysBirthday birthday, int daysUntilBirthday);
}
