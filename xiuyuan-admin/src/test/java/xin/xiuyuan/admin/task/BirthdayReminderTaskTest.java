package xin.xiuyuan.admin.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BirthdayReminderTaskTest {

    @Autowired
    private BirthdayReminderTask birthdayReminderTask;

    @Test
    void checkBirthdayReminder() {
        birthdayReminderTask.checkBirthdayReminder();
    }
}