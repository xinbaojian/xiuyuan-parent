package xin.xiuyuan.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xin.xiuyuan.admin.annotation.DataScope;
import xin.xiuyuan.admin.dto.birthday.SysBirthdayForm;
import xin.xiuyuan.admin.dto.birthday.SysBirthdayPageQuery;
import xin.xiuyuan.admin.entity.SysBirthday;
import xin.xiuyuan.admin.mapper.birthday.SysBirthdayMapper;
import xin.xiuyuan.admin.repository.SysBirthdayRepository;
import xin.xiuyuan.admin.service.ISysBirthdayService;
import xin.xiuyuan.admin.util.DataScopeHelper;
import xin.xiuyuan.admin.vo.birthday.SysBirthdayPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.types.BirthdayType;
import xin.xiuyuan.common.util.LunarCalendarUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 生日管理 ServiceImpl
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SysBirthdayServiceImpl implements ISysBirthdayService {

    private final SysBirthdayRepository birthdayRepository;
    private final MongoTemplate mongoTemplate;
    private final SysBirthdayMapper birthdayMapper;

    @Override
    public ApiResult<String> save(SysBirthdayForm form) {
        // 校验姓名是否已存在
        SysBirthday nameCheck = birthdayRepository.findByName(form.getName());
        Assert.isNull(nameCheck, "姓名已存在");

        SysBirthday birthday = birthdayMapper.toEntity(form);

        // 自动计算下次生日（支持闰月）
        LocalDate nextBirthday = LunarCalendarUtil.calculateNextBirthday(
                form.getBirthDate(),
                form.getBirthdayType(),
                form.getIsLeapMonth() != null ? form.getIsLeapMonth() : false
        );
        birthday.setNextBirthday(nextBirthday);

        // 计算年龄
        int age = LunarCalendarUtil.calculateAge(form.getBirthDate());
        birthday.setAge(age);

        // 计算距离生日天数
        int daysUntil = (int) ChronoUnit.DAYS.between(LocalDate.now(), nextBirthday);
        birthday.setDaysUntilBirthday(daysUntil);

        // 设置创建者为当前登录用户
        birthday.setCreateBy(StpUtil.getLoginIdAsString());
        birthdayRepository.save(birthday);
        return ApiResult.success("新增生日成功");
    }

    @Override
    public ApiResult<String> update(String id, SysBirthdayForm form) {
        // 校验生日 ID 是否存在
        SysBirthday birthday = birthdayRepository.findById(id).orElse(null);
        Assert.notNull(birthday, "生日记录不存在");

        // 校验姓名是否已存在
        SysBirthday nameCheck = birthdayRepository.findByNameAndIdNot(form.getName(), id);
        Assert.isNull(nameCheck, "姓名已存在");

        birthdayMapper.updateEntity(form, birthday);

        // 自动计算下次生日（支持闰月）
        LocalDate nextBirthday = LunarCalendarUtil.calculateNextBirthday(
                form.getBirthDate(),
                form.getBirthdayType(),
                form.getIsLeapMonth() != null ? form.getIsLeapMonth() : false
        );
        birthday.setNextBirthday(nextBirthday);

        // 计算年龄
        int age = LunarCalendarUtil.calculateAge(form.getBirthDate());
        birthday.setAge(age);

        // 计算距离生日天数
        int daysUntil = (int) ChronoUnit.DAYS.between(LocalDate.now(), nextBirthday);
        birthday.setDaysUntilBirthday(daysUntil);

        // 设置更新者为当前登录用户
        birthday.setUpdateBy(StpUtil.getLoginIdAsString());
        birthdayRepository.save(birthday);
        return ApiResult.success("编辑生日成功");
    }

    @Override
    public ApiResult<String> delete(String id) {
        // 校验生日是否存在
        SysBirthday birthday = birthdayRepository.findById(id).orElse(null);
        Assert.notNull(birthday, "生日记录不存在");
        birthdayRepository.delete(birthday);
        return ApiResult.success("删除生日成功");
    }

    @Override
    public ApiResult<SysBirthdayPageVO> getById(String id) {
        SysBirthday birthday = birthdayRepository.findById(id).orElse(null);
        Assert.notNull(birthday, "生日记录不存在");

        SysBirthdayPageVO vo = birthdayMapper.toVO(birthday);

        // 从数据库读取年龄（定时任务已更新）
        // 如果数据库中没有，则实时计算
        if (birthday.getAge() != null) {
            vo.setAge(birthday.getAge());
        } else {
            vo.setAge(LunarCalendarUtil.calculateAge(birthday.getBirthDate()));
        }

        // 从数据库读取距离生日天数（定时任务已更新）
        // 如果数据库中没有，则实时计算
        if (birthday.getDaysUntilBirthday() != null) {
            vo.setDaysUntilBirthday(birthday.getDaysUntilBirthday());
        } else {
            int daysUntil = (int) ChronoUnit.DAYS.between(LocalDate.now(), birthday.getNextBirthday());
            vo.setDaysUntilBirthday(daysUntil);
        }

        // 判断是否过农历生日（处理 null 情况，默认为公历）
        BirthdayType type = birthday.getBirthdayType() != null ? birthday.getBirthdayType() : BirthdayType.SOLAR;
        vo.setCelebrateLunar(type == BirthdayType.LUNAR);

        // 设置默认值，避免前端显示 null
        if (vo.getBirthdayType() == null) {
            vo.setBirthdayType(type);
        }
        if (vo.getIsLeapMonth() == null) {
            vo.setIsLeapMonth(birthday.getIsLeapMonth() != null ? birthday.getIsLeapMonth() : false);
        }

        // 如果是农历生日,显示农历日期
        if (type == BirthdayType.LUNAR) {
            // birthDate 本身就是农历日期，直接格式化
            int lunarYear = birthday.getBirthDate().getYear();
            int lunarMonth = birthday.getBirthDate().getMonthValue();
            int lunarDay = birthday.getBirthDate().getDayOfMonth();
            boolean isLeap = birthday.getIsLeapMonth() != null && birthday.getIsLeapMonth();
            vo.setBirthDateLunar(LunarCalendarUtil.formatLunarDate(lunarYear, lunarMonth, lunarDay, isLeap));
        }

        return ApiResult.success(vo);
    }

    @Override
    @DataScope
    public ApiResult<PageData<SysBirthdayPageVO>> list(SysBirthdayPageQuery pageQuery) {
        Pageable pageable = PageRequest.of(pageQuery.getPage(), pageQuery.getPageSize());

        // 构建动态查询条件
        Criteria criteria = new Criteria();

        // 应用数据权限过滤(基于 createBy 字段)
        DataScopeHelper.applyDataScopeByCreator(criteria);

        // 根据查询参数动态添加条件
        if (StrUtil.isNotBlank(pageQuery.getName())) {
            criteria.and("name").regex(pageQuery.getName(), "i");
        }
        if (pageQuery.getGender() != null) {
            criteria.and("gender").is(pageQuery.getGender());
        }
        if (pageQuery.getBirthdayType() != null) {
            criteria.and("birthdayType").is(pageQuery.getBirthdayType());
        }
        if (pageQuery.getStatus() != null) {
            criteria.and("status").is(pageQuery.getStatus());
        }

        // 出生日期范围查询
        if (pageQuery.getBirthDateStart() != null && pageQuery.getBirthDateEnd() != null) {
            criteria.and("birthDate").gte(pageQuery.getBirthDateStart()).lte(pageQuery.getBirthDateEnd());
        } else if (pageQuery.getBirthDateStart() != null) {
            criteria.and("birthDate").gte(pageQuery.getBirthDateStart());
        } else if (pageQuery.getBirthDateEnd() != null) {
            criteria.and("birthDate").lte(pageQuery.getBirthDateEnd());
        }

        // 下次生日范围查询
        if (pageQuery.getNextBirthdayStart() != null && pageQuery.getNextBirthdayEnd() != null) {
            criteria.and("nextBirthday").gte(pageQuery.getNextBirthdayStart()).lte(pageQuery.getNextBirthdayEnd());
        } else if (pageQuery.getNextBirthdayStart() != null) {
            criteria.and("nextBirthday").gte(pageQuery.getNextBirthdayStart());
        } else if (pageQuery.getNextBirthdayEnd() != null) {
            criteria.and("nextBirthday").lte(pageQuery.getNextBirthdayEnd());
        }

        Query query = new Query(criteria);
        // 设置分页和排序
        query.with(pageable).with(Sort.by(Sort.Direction.ASC, "nextBirthday"));
        // 执行查询
        List<SysBirthday> birthdayList = mongoTemplate.find(query, SysBirthday.class);

        // 查询总数
        long total = mongoTemplate.count(new Query(criteria), SysBirthday.class);

        // 转换为 VO 对象并计算动态字段
        List<SysBirthdayPageVO> voList = birthdayList.stream()
                .map(birthday -> {
                    SysBirthdayPageVO vo = birthdayMapper.toVO(birthday);

                    // 从数据库读取年龄（定时任务已更新）
                    // 如果数据库中没有，则实时计算
                    if (birthday.getAge() != null) {
                        vo.setAge(birthday.getAge());
                    } else {
                        vo.setAge(LunarCalendarUtil.calculateAge(birthday.getBirthDate()));
                    }

                    // 从数据库读取距离生日天数（定时任务已更新）
                    // 如果数据库中没有，则实时计算
                    if (birthday.getDaysUntilBirthday() != null) {
                        vo.setDaysUntilBirthday(birthday.getDaysUntilBirthday());
                    } else {
                        int daysUntil = (int) ChronoUnit.DAYS.between(LocalDate.now(), birthday.getNextBirthday());
                        vo.setDaysUntilBirthday(daysUntil);
                    }

                    // 判断是否过农历生日（处理 null 情况，默认为公历）
                    BirthdayType type = birthday.getBirthdayType() != null ? birthday.getBirthdayType() : BirthdayType.SOLAR;
                    vo.setCelebrateLunar(type == BirthdayType.LUNAR);

                    // 设置默认值，避免前端显示 null
                    if (vo.getBirthdayType() == null) {
                        vo.setBirthdayType(type);
                    }
                    if (vo.getIsLeapMonth() == null) {
                        vo.setIsLeapMonth(birthday.getIsLeapMonth() != null ? birthday.getIsLeapMonth() : false);
                    }

                    // 如果是农历生日,显示农历日期
                    if (type == BirthdayType.LUNAR) {
                        // birthDate 本身就是农历日期，直接格式化
                        int lunarYear = birthday.getBirthDate().getYear();
                        int lunarMonth = birthday.getBirthDate().getMonthValue();
                        int lunarDay = birthday.getBirthDate().getDayOfMonth();
                        boolean isLeap = birthday.getIsLeapMonth() != null && birthday.getIsLeapMonth();
                        vo.setBirthDateLunar(LunarCalendarUtil.formatLunarDate(lunarYear, lunarMonth, lunarDay, isLeap));
                    }

                    return vo;
                })
                .collect(Collectors.toList());

        // 构造 PageData 对象
        PageData<SysBirthdayPageVO> pageData = new PageData<>();
        pageData.setList(voList);
        pageData.setTotal(total);
        return ApiResult.success(pageData);
    }
}
