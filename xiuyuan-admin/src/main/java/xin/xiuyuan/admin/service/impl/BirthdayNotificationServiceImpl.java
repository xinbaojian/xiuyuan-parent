package xin.xiuyuan.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xin.xiuyuan.admin.config.BarkProperties;
import xin.xiuyuan.admin.entity.SysBirthday;
import xin.xiuyuan.admin.service.IBirthdayNotificationService;
import xin.xiuyuan.common.util.BarkUtils;

/**
 * 生日提醒通知服务实现(默认实现,仅打印日志)
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class BirthdayNotificationServiceImpl implements IBirthdayNotificationService {

    private final BarkProperties barkProperties;

    @Override
    public void sendReminder(SysBirthday birthday, int daysUntilBirthday) {
        log.info("生日提醒: [{}] 的生日还有 [{}] 天, 出生日期: [{}], 下次生日: [{}], 备注: [{}]",
                birthday.getName(),
                daysUntilBirthday,
                birthday.getBirthDate(),
                birthday.getNextBirthday(),
                birthday.getRemark());
        BarkUtils.BarkPostBody barkPostBody = new BarkUtils.BarkPostBody();
        barkPostBody.setTitle("生日提醒")
                .setSubtitle("还有 " + daysUntilBirthday + " 天")
                .setBody(birthday.getName() + " 的生日还有 " + daysUntilBirthday + " 天");
        BarkUtils.sendPost(barkProperties.getUrl(), barkPostBody);
    }
}
