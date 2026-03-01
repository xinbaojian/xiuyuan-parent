package xin.xiuyuan.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import xin.xiuyuan.common.types.BirthdayType;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.Gender;
import xin.xiuyuan.domain.entity.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 生日管理实体
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Data
@NoArgsConstructor
@Document
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysBirthday extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    /**
     * 姓名
     */
    @Indexed
    private String name;

    /**
     * 年龄
     * 每天定时任务自动更新
     */
    private Integer age;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 出生日期
     * - SOLAR(公历): birthDate 表示公历日期
     * - LUNAR(农历): birthDate 表示农历日期（如 2018-10-03 表示农历2018年十月初三）
     */
    @Indexed
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * 生日类型
     */
    @Indexed
    private BirthdayType birthdayType;

    /**
     * 是否闰月（仅农历生日有效）
     * 如果是闰月出生，设置为 true
     */
    private Boolean isLeapMonth = false;

    /**
     * 下次生日(公历)
     * 新增或编辑时自动计算
     */
    @Indexed
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nextBirthday;

    /**
     * 距离下次生日天数
     * 每天定时任务自动更新
     */
    private Integer daysUntilBirthday;

    /**
     * 提前提醒天数
     */
    private Integer advanceReminderDays;

    /**
     * 状态
     */
    @Indexed
    private CommonStatus status = CommonStatus.NORMAL;
}
