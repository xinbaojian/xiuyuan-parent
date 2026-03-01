package xin.xiuyuan.admin.dto.birthday;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.BirthdayType;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.Gender;

import java.time.LocalDate;

/**
 * 生日表单 DTO
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Data
@Accessors(chain = true)
public class SysBirthdayForm {

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 出生日期
     * - SOLAR(公历): birthDate 表示公历日期
     * - LUNAR(农历): birthDate 表示农历日期（如 2018-10-03 表示农历2018年十月初三）
     */
    @NotNull(message = "出生日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthDate;

    /**
     * 生日类型
     */
    @NotNull(message = "生日类型不能为空")
    private BirthdayType birthdayType;

    /**
     * 是否闰月（仅农历生日有效，默认false）
     * 如果是闰月出生，设置为 true
     */
    private Boolean isLeapMonth = false;

    /**
     * 提前提醒天数
     */
    @NotNull(message = "提前提醒天数不能为空")
    @Min(value = 1, message = "提前提醒天数至少为1天")
    private Integer advanceReminderDays;

    /**
     * 状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 备注
     */
    private String remark;
}
