package xin.xiuyuan.admin.dto.birthday;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xin.xiuyuan.admin.dto.BasePageQuery;
import xin.xiuyuan.common.types.BirthdayType;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.Gender;

import java.time.LocalDate;

/**
 * 生日分页查询参数类
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysBirthdayPageQuery extends BasePageQuery {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 生日类型
     */
    private BirthdayType birthdayType;

    /**
     * 状态
     */
    private CommonStatus status;

    /**
     * 出生日期-查询条件
     */
    private LocalDate birthDateStart;

    /**
     * 出生日期-查询条件
     */
    private LocalDate birthDateEnd;

    /**
     * 下次生日-查询条件
     */
    private LocalDate nextBirthdayStart;

    /**
     * 下次生日-查询条件
     */
    private LocalDate nextBirthdayEnd;
}
