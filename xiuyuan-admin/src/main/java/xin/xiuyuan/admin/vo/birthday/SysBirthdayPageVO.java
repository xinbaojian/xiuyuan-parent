package xin.xiuyuan.admin.vo.birthday;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import xin.xiuyuan.common.types.BirthdayType;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.Gender;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 生日分页查询VO
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Data
public class SysBirthdayPageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Gender gender;
    /**
     * 距离生日天数
     */
    private Integer daysUntilBirthday;
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthDate;
    /**
     * 生日类型
     */
    private BirthdayType birthdayType;
    /**
     * 是否闰月（仅农历生日有效）
     */
    private Boolean isLeapMonth;
    /**
     * 出生日期(农历)
     */
    private String birthDateLunar;
    /**
     * 下次生日(公历)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate nextBirthday;
    /**
     * 过农历生日
     */
    private Boolean celebrateLunar;
    /**
     * 状态
     */
    private CommonStatus status;
    /**
     * 提前提醒天数
     */
    private Integer advanceReminderDays;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 备注
     */
    private String remark;

    /**
     * 性别描述
     */
    public String getGenderDesc() {
        return gender == null ? null : gender.getDesc();
    }

    /**
     * 生日类型描述
     */
    public String getBirthdayTypeDesc() {
        return birthdayType == null ? null : birthdayType.getDesc();
    }

    /**
     * 状态描述
     */
    public String getStatusDesc() {
        return status == null ? null : status.getDesc();
    }
}
