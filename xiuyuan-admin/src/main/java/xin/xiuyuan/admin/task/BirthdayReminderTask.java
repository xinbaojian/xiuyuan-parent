package xin.xiuyuan.admin.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xin.xiuyuan.admin.entity.SysBirthday;
import xin.xiuyuan.admin.repository.SysBirthdayRepository;
import xin.xiuyuan.admin.service.IBirthdayNotificationService;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.util.LunarCalendarUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * 生日提醒定时任务
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class BirthdayReminderTask {

    private final SysBirthdayRepository birthdayRepository;
    private final IBirthdayNotificationService notificationService;

    /**
     * 每天更新距离生日天数
     * 每天凌晨0点执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateDaysUntilBirthday() {
        log.info("开始更新距离生日天数");

        try {
            // 查询所有生日记录
            List<SysBirthday> birthdays = birthdayRepository.findAll();

            if (birthdays.isEmpty()) {
                log.info("没有生日记录需要更新");
                return;
            }

            LocalDate today = LocalDate.now();
            int updatedCount = 0;

            for (SysBirthday birthday : birthdays) {
                if (birthday.getNextBirthday() == null) {
                    continue;
                }

                // 计算距离下次生日天数
                int daysUntil = (int) java.time.temporal.ChronoUnit.DAYS.between(today, birthday.getNextBirthday());

                // 计算年龄
                int age = LunarCalendarUtil.calculateAge(birthday.getBirthDate());
                birthday.setAge(age);

                // 如果生日已过了,计算下一次生日
                if (daysUntil < 0) {
                    LocalDate nextBirthday = LunarCalendarUtil.calculateNextBirthday(
                            birthday.getBirthDate(),
                            birthday.getBirthdayType(),
                            birthday.getIsLeapMonth() != null ? birthday.getIsLeapMonth() : false
                    );
                    birthday.setNextBirthday(nextBirthday);

                    // 重新计算距离生日天数
                    daysUntil = (int) java.time.temporal.ChronoUnit.DAYS.between(today, nextBirthday);
                }

                birthday.setDaysUntilBirthday(daysUntil);
                birthdayRepository.save(birthday);
                updatedCount++;
            }

            log.info("更新距离生日天数完成, 共更新 {} 个记录", updatedCount);

        } catch (Exception e) {
            log.error("更新距离生日天数任务执行失败", e);
        }
    }

    /**
     * 生日提醒检查
     * 每天早上7点执行
     */
    @Scheduled(cron = "0 0 7 * * ?")
    public void checkBirthdayReminder() {
        log.info("开始检查生日提醒");

        try {
            // 查询所有启用状态的生日
            List<SysBirthday> birthdays = birthdayRepository.findByStatus(CommonStatus.NORMAL);

            if (birthdays.isEmpty()) {
                log.info("没有启用的生日需要检查");
                return;
            }

            LocalDate today = LocalDate.now();
            int reminderCount = 0;
            int updatedCount = 0;

            for (SysBirthday birthday : birthdays) {
                if (birthday.getNextBirthday() == null || birthday.getAdvanceReminderDays() == null) {
                    continue;
                }

                // 计算年龄
                int age = LunarCalendarUtil.calculateAge(birthday.getBirthDate());
                birthday.setAge(age);

                // 计算距离下次生日天数
                int daysUntil = (int) java.time.temporal.ChronoUnit.DAYS.between(today, birthday.getNextBirthday());

                // 如果生日已过了,计算下一次生日
                if (daysUntil < 0) {
                    LocalDate nextBirthday = LunarCalendarUtil.calculateNextBirthday(
                            birthday.getBirthDate(),
                            birthday.getBirthdayType(),
                            birthday.getIsLeapMonth() != null ? birthday.getIsLeapMonth() : false
                    );
                    birthday.setNextBirthday(nextBirthday);

                    // 重新计算距离生日天数
                    daysUntil = (int) java.time.temporal.ChronoUnit.DAYS.between(today, nextBirthday);
                    birthday.setDaysUntilBirthday(daysUntil);

                    birthdayRepository.save(birthday);
                    updatedCount++;
                    log.info("生日已过,更新下次生日: [{}], 原生日: [{}], 新生日: [{}], 距离天数: [{}], 年龄: [{}]",
                            birthday.getName(), birthday.getNextBirthday(), nextBirthday, daysUntil, age);
                } else {
                    // 只更新年龄和距离天数
                    birthday.setDaysUntilBirthday(daysUntil);
                    birthdayRepository.save(birthday);
                }

                // 如果距离生日天数小于等于提前提醒天数,发送提醒
                if (daysUntil <= birthday.getAdvanceReminderDays() && daysUntil >= 0) {
                    reminderCount++;
                    notificationService.sendReminder(birthday, daysUntil);
                }
            }

            log.info("生日提醒检查完成, 共检查 {} 个生日, 触发提醒 {} 个, 更新下次生日 {} 个",
                    birthdays.size(), reminderCount, updatedCount);

        } catch (Exception e) {
            log.error("生日提醒检查任务执行失败", e);
        }
    }
}
