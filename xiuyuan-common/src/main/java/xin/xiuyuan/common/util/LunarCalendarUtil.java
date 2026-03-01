package xin.xiuyuan.common.util;

import cn.hutool.core.date.ChineseDate;
import xin.xiuyuan.common.types.BirthdayType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 农历工具类 - 基于 Hutool
 * 使用 Hutool 的 ChineseDate 实现公历和农历的准确转换
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
public class LunarCalendarUtil {

    /**
     * 将公历日期转换为农历日期
     *
     * @param solarDate 公历日期
     * @return 农历数组 [年, 月, 日, 是否闰月(1是0否)]
     */
    public static int[] solarToLunar(LocalDate solarDate) {
        // 转换为 Date
        Date date = Date.from(solarDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        ChineseDate chineseDate = new ChineseDate(date);

        // 获取农历信息
        int year = chineseDate.getChineseYear();
        int month = chineseDate.getMonth();
        int day = chineseDate.getDay();

        // 判断是否闰月:通过toString结果中是否包含"闰"字来判断
        String chineseStr = chineseDate.toString();
        boolean isLeap = chineseStr.contains("闰");

        return new int[]{year, month, day, isLeap ? 1 : 0};
    }

    /**
     * 将农历日期转换为公历日期
     *
     * @param lunarYear  农历年
     * @param lunarMonth 农历月
     * @param lunarDay   农历日
     * @param isLeap     是否闰月
     * @return 公历日期
     */
    public static LocalDate lunarToSolar(int lunarYear, int lunarMonth, int lunarDay, boolean isLeap) {
        ChineseDate chineseDate = new ChineseDate(lunarYear, lunarMonth, lunarDay, isLeap);
        Date date = chineseDate.getGregorianDate();

        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * 格式化农历日期为中文
     */
    public static String formatLunarDate(int[] lunar) {
        ChineseDate chineseDate = new ChineseDate(lunar[0], lunar[1], lunar[2], lunar[3] == 1);
        return chineseDate.toString();
    }

    /**
     * 格式化农历日期为中文
     *
     * @param lunarYear  农历年
     * @param lunarMonth 农历月
     * @param lunarDay   农历日
     * @param isLeap     是否闰月
     * @return 农历日期字符串
     */
    public static String formatLunarDate(int lunarYear, int lunarMonth, int lunarDay, boolean isLeap) {
        ChineseDate chineseDate = new ChineseDate(lunarYear, lunarMonth, lunarDay, isLeap);
        return chineseDate.toString();
    }

    /**
     * 计算下次生日（不支持闰月）
     */
    public static LocalDate calculateNextBirthday(LocalDate birthDate, BirthdayType birthdayType) {
        return calculateNextBirthday(birthDate, birthdayType, false);
    }

    /**
     * 计算下次生日（支持闰月）
     *
     * @param birthDate    出生日期
     *                     - SOLAR: 公历日期
     *                     - LUNAR: 农历日期（如 2018-10-03 表示农历2018年十月初三）
     * @param birthdayType 生日类型
     * @param isLeapMonth  是否闰月（仅农历生日有效）
     * @return 下次生日的公历日期
     */
    public static LocalDate calculateNextBirthday(LocalDate birthDate, BirthdayType birthdayType, boolean isLeapMonth) {
        if (birthdayType == null || birthdayType.equals(BirthdayType.SOLAR)) {
            return calculateSolarBirthday(birthDate);
        } else {
            return calculateLunarBirthday(birthDate, isLeapMonth);
        }
    }

    private static LocalDate calculateSolarBirthday(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        int year = today.getYear();

        try {
            LocalDate birthday = LocalDate.of(year, birthDate.getMonth(), birthDate.getDayOfMonth());
            if (birthday.isBefore(today) || birthday.isEqual(today)) {
                birthday = LocalDate.of(year + 1, birthDate.getMonth(), birthDate.getDayOfMonth());
            }
            return birthday;
        } catch (Exception e) {
            // 处理2月29日等特殊日期
            return LocalDate.of(year + 1, birthDate.getMonth(), birthDate.getDayOfMonth());
        }
    }

    /**
     * 计算农历生日
     *
     * @param birthDate   农历日期（如 2018-10-03 表示农历2018年十月初三）
     * @param isLeapMonth 是否闰月
     * @return 下次生日的公历日期
     */
    private static LocalDate calculateLunarBirthday(LocalDate birthDate, boolean isLeapMonth) {
        // birthDate 在 birthdayType = LUNAR 时，直接代表农历日期（年-月-日）
        // 例如：2018-10-03 表示农历2018年十月初三
        int lunarMonth = birthDate.getMonthValue();
        int lunarDay = birthDate.getDayOfMonth();

        LocalDate today = LocalDate.now();

        try {
            // 计算今年的农历生日对应的公历日期
            LocalDate thisYear = lunarToSolar(today.getYear(), lunarMonth, lunarDay, isLeapMonth);

            if (thisYear.isBefore(today) || thisYear.isEqual(today)) {
                // 今年的生日已过,计算明年的
                return lunarToSolar(today.getYear() + 1, lunarMonth, lunarDay, isLeapMonth);
            }

            return thisYear;
        } catch (Exception e) {
            // 如果转换失败（可能是该年没有闰月），尝试明年或使用非闰月
            try {
                return lunarToSolar(today.getYear() + 1, lunarMonth, lunarDay, isLeapMonth);
            } catch (Exception ex) {
                // 如果还是失败，尝试使用非闰月
                return lunarToSolar(today.getYear() + 1, lunarMonth, lunarDay, false);
            }
        }
    }

    /**
     * 计算年龄
     */
    public static int calculateAge(LocalDate birthDate) {
        return (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}
