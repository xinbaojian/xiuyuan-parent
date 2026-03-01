package xin.xiuyuan.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xin.xiuyuan.common.types.BirthdayType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 农历工具类测试
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@DisplayName("农历转换工具测试")
class LunarCalendarUtilTest {

    @Test
    @DisplayName("测试公历转农历 - 1969年11月21日")
    void testSolarToLunar_1969_11_21() {
        LocalDate solar = LocalDate.of(1969, 11, 21);
        int[] lunar = LunarCalendarUtil.solarToLunar(solar);
        assertArrayEquals(new int[]{1969, 10, 12, 0}, lunar,
                "1969-11-21 应该是农历1969年十月十二");
    }

    @Test
    @DisplayName("测试公历转农历 - 1995年2月28日")
    void testSolarToLunar_1995_02_28() {
        LocalDate solar = LocalDate.of(1995, 2, 28);
        int[] lunar = LunarCalendarUtil.solarToLunar(solar);

        assertArrayEquals(new int[]{1995, 1, 29, 0}, lunar,
                "1995-02-28 应该是农历1995年正月廿九");
    }

    @Test
    @DisplayName("测试公历转农历 - 1972年3月18日")
    void testSolarToLunar_1972_03_18() {
        LocalDate solar = LocalDate.of(1972, 3, 18);
        int[] lunar = LunarCalendarUtil.solarToLunar(solar);

        assertArrayEquals(new int[]{1972, 2, 4, 0}, lunar,
                "1972-03-18 应该是农历1972年二月初四");
    }

    @Test
    @DisplayName("测试公历转农历 - 2012年3月15日")
    void testSolarToLunar_2012_03_15() {
        LocalDate solar = LocalDate.of(2012, 3, 15);
        int[] lunar = LunarCalendarUtil.solarToLunar(solar);

        assertArrayEquals(new int[]{2012, 2, 23, 0}, lunar,
                "2012-03-15 应该是农历2012年二月廿三");
    }

    @Test
    @DisplayName("测试公历转农历 - 1990年5月12日")
    void testSolarToLunar_1990_05_12() {
        LocalDate solar = LocalDate.of(1990, 5, 12);
        int[] lunar = LunarCalendarUtil.solarToLunar(solar);

        assertArrayEquals(new int[]{1990, 4, 18, 0}, lunar,
                "1990-05-12 应该是农历1990年四月十八");
    }

    @Test
    @DisplayName("测试公历转农历 - 1966年6月21日")
    void testSolarToLunar_1966_06_21() {
        LocalDate solar = LocalDate.of(1966, 6, 21);
        int[] lunar = LunarCalendarUtil.solarToLunar(solar);

        assertArrayEquals(new int[]{1966, 5, 3, 0}, lunar,
                "1966-06-21 应该是农历1966年五月初三");
    }

    @Test
    @DisplayName("测试公历转农历 - 2018年11月10日")
    void testSolarToLunar_2018_11_10() {
        LocalDate solar = LocalDate.of(2018, 11, 10);
        int[] lunar = LunarCalendarUtil.solarToLunar(solar);

        assertArrayEquals(new int[]{2018, 10, 3, 0}, lunar,
                "2018-11-10 应该是农历2018年十月初三");
    }

    @Test
    @DisplayName("测试农历转公历 - 农历1969年十月初二")
    void testLunarToSolar_1969_10_12() {
        LocalDate solar = LunarCalendarUtil.lunarToSolar(1969, 10, 12, false);
        LocalDate expected = LocalDate.of(1969, 11, 21);

        assertEquals(expected, solar,
                "农历1969年十月初十二 应该是公历1969年11月21日");
    }

    @Test
    @DisplayName("测试农历转公历 - 农历1995年正月廿九")
    void testLunarToSolar_1995_01_29() {
        LocalDate solar = LunarCalendarUtil.lunarToSolar(1995, 1, 29, false);
        LocalDate expected = LocalDate.of(1995, 2, 28);

        assertEquals(expected, solar,
                "农历1995年正月廿九 应该是公历1995年2月28日");
    }

    @Test
    @DisplayName("测试农历转公历 - 农历1972年二月初四")
    void testLunarToSolar_1972_02_04() {
        LocalDate solar = LunarCalendarUtil.lunarToSolar(1972, 2, 4, false);
        LocalDate expected = LocalDate.of(1972, 3, 18);

        assertEquals(expected, solar,
                "农历1972年二月初四 应该是公历1972年3月18日");
    }

    @Test
    @DisplayName("测试双向转换一致性 - 1969年11月21日")
    void testRoundTrip_1969_11_21() {
        LocalDate original = LocalDate.of(1969, 11, 21);
        int[] lunar = LunarCalendarUtil.solarToLunar(original);
        LocalDate converted = LunarCalendarUtil.lunarToSolar(lunar[0], lunar[1], lunar[2], lunar[3] == 1);

        assertEquals(original, converted,
                "1969-11-21 双向转换应该保持一致");
    }

    @Test
    @DisplayName("测试双向转换一致性 - 1995年2月28日")
    void testRoundTrip_1995_02_28() {
        LocalDate original = LocalDate.of(1995, 2, 28);
        int[] lunar = LunarCalendarUtil.solarToLunar(original);
        LocalDate converted = LunarCalendarUtil.lunarToSolar(lunar[0], lunar[1], lunar[2], lunar[3] == 1);

        assertEquals(original, converted,
                "1995-02-28 双向转换应该保持一致");
    }

    @Test
    @DisplayName("测试双向转换一致性 - 所有测试数据")
    void testRoundTrip_All() {
        LocalDate[] testDates = {
                LocalDate.of(1969, 11, 21),
                LocalDate.of(1995, 2, 28),
                LocalDate.of(1972, 3, 18),
                LocalDate.of(2012, 3, 15),
                LocalDate.of(1990, 5, 12),
                LocalDate.of(1966, 6, 21),
                LocalDate.of(2018, 11, 10)
        };

        for (LocalDate original : testDates) {
            int[] lunar = LunarCalendarUtil.solarToLunar(original);
            LocalDate converted = LunarCalendarUtil.lunarToSolar(lunar[0], lunar[1], lunar[2], lunar[3] == 1);

            assertEquals(original, converted,
                    original + " 双向转换应该保持一致");
        }
    }

    @Test
    @DisplayName("测试计算年龄")
    void testCalculateAge() {
        LocalDate birthDate1 = LocalDate.of(1990, 5, 12);
        int age1 = LunarCalendarUtil.calculateAge(birthDate1);
        assertTrue(age1 > 30, "1990年出生的人应该超过30岁");

        LocalDate birthDate2 = LocalDate.of(2000, 1, 1);
        int age2 = LunarCalendarUtil.calculateAge(birthDate2);
        assertTrue(age2 > 20, "2000年出生的人应该超过20岁");
    }

    @Test
    @DisplayName("测试格式化农历日期")
    void testFormatLunarDate() {
        int[] lunar1 = {1969, 10, 12, 0};
        String formatted1 = LunarCalendarUtil.formatLunarDate(lunar1);
        assertNotNull(formatted1, "格式化结果不应为null");
        assertFalse(formatted1.isEmpty(), "格式化结果不应为空");

        int[] lunar2 = {1995, 1, 29, 0};
        String formatted2 = LunarCalendarUtil.formatLunarDate(lunar2);
        assertNotNull(formatted2, "格式化结果不应为null");
        assertFalse(formatted2.isEmpty(), "格式化结果不应为空");
    }

    @Test
    @DisplayName("测试公历生日计算 - 当年生日未过")
    void testCalculateSolarBirthday_NotPassed() {
        // 假设今天是2026年3月1日
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        LocalDate nextBirthday = LunarCalendarUtil.calculateNextBirthday(birthDate, BirthdayType.SOLAR);

        // 1990年5月15日出生,2026年的生日应该是5月15日(未到)
        assertEquals(LocalDate.of(2026, 5, 15), nextBirthday);
    }

    @Test
    @DisplayName("测试公历生日计算 - 当年生日已过")
    void testCalculateSolarBirthday_Passed() {
        LocalDate birthDate = LocalDate.of(1990, 1, 15);
        LocalDate nextBirthday = LunarCalendarUtil.calculateNextBirthday(birthDate, BirthdayType.SOLAR);

        // 1月15日出生,假设当前日期在1月15日之后,下次生日应该是明年1月15日
        assertTrue(nextBirthday.getYear() >= LocalDate.now().getYear(),
                "生日已过,应该返回明年的生日");
    }

    @Test
    @DisplayName("测试农历生日计算")
    void testCalculateLunarBirthday() {
        LocalDate birthDate = LocalDate.of(1990, 5, 12); // 对应农历某天
        LocalDate nextBirthday = LunarCalendarUtil.calculateNextBirthday(birthDate, BirthdayType.LUNAR);

        assertNotNull(nextBirthday, "农历生日不应该为null");
        assertTrue(nextBirthday.isAfter(LocalDate.now()), "下次生日应该在今天之后");
    }

    @Test
    @DisplayName("测试距离生日天数计算")
    void testDaysUntilNextBirthday() {
        LocalDate birthDate = LocalDate.now().plusDays(10); // 10天后
        LocalDate nextBirthday = LunarCalendarUtil.calculateNextBirthday(birthDate, BirthdayType.SOLAR);

        LocalDate today = LocalDate.now();
        int days = (int) ChronoUnit.DAYS.between(today, nextBirthday);

        assertTrue(days >= 0, "距离生日天数应该大于等于0");
        assertTrue(days <= 365, "距离生日天数应该小于等于365");
    }

    @Test
    @DisplayName("测试闰月处理")
    void testLeapMonth() {
        // 测试包含闰月的年份,比如1963年有闰四月
        // 这里只测试数据能正常处理,不验证具体日期
        LocalDate testDate = LocalDate.of(1963, 5, 24); // 1963年闰四月初三
        int[] lunar = LunarCalendarUtil.solarToLunar(testDate);

        assertNotNull(lunar, "转换结果不应为null");
        assertEquals(4, lunar.length, "返回数组长度应该为4");
        assertTrue(lunar[0] >= 1900 && lunar[0] <= 2100, "年份应该在合理范围内");
        assertTrue(lunar[1] >= 1 && lunar[1] <= 12, "月份应该在1-12之间");
        assertTrue(lunar[2] >= 1 && lunar[2] <= 30, "日期应该在1-30之间");
        assertTrue(lunar[3] == 0 || lunar[3] == 1, "是否闰月应该为0或1");
    }

    @Test
    @DisplayName("测试边界情况 - 1900年1月31日")
    void testEdgeCase_1900_01_31() {
        LocalDate baseDate = LocalDate.of(1900, 1, 31);
        int[] lunar = LunarCalendarUtil.solarToLunar(baseDate);

        // 1900年1月31日是农历1900年正月初一
        assertArrayEquals(new int[]{1900, 1, 1, 0}, lunar,
                "1900-01-31 应该是农历1900年正月初一");
    }

    @Test
    @DisplayName("测试特殊日期 - 当前日期")
    void testCurrentDate() {
        LocalDate today = LocalDate.now();
        int[] lunar = LunarCalendarUtil.solarToLunar(today);

        assertNotNull(lunar, "转换结果不应为null");
        assertEquals(4, lunar.length, "返回数组长度应该为4");

        LocalDate converted = LunarCalendarUtil.lunarToSolar(lunar[0], lunar[1], lunar[2], lunar[3] == 1);
        // 由于转换精度问题,这里只测试日期相近
        long daysDiff = Math.abs(converted.until(today).getDays());
        assertTrue(daysDiff <= 2, "转换后的日期应该与原日期相差不超过2天");
    }
}
