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
import xin.xiuyuan.admin.dto.subscription.SysSubscriptionForm;
import xin.xiuyuan.admin.dto.subscription.SysSubscriptionPageQuery;
import xin.xiuyuan.admin.entity.SysSubscription;
import xin.xiuyuan.admin.mapper.subscription.SysSubscriptionMapper;
import xin.xiuyuan.admin.repository.SysSubscriptionRepository;
import xin.xiuyuan.admin.service.ISysSubscriptionService;
import xin.xiuyuan.admin.util.DataScopeHelper;
import xin.xiuyuan.admin.vo.subscription.SysSubscriptionPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订阅管理 ServiceImpl
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SysSubscriptionServiceImpl implements ISysSubscriptionService {

    private final SysSubscriptionRepository subscriptionRepository;
    private final MongoTemplate mongoTemplate;
    private final SysSubscriptionMapper subscriptionMapper;

    @Override
    public ApiResult<String> save(SysSubscriptionForm form) {
        // 校验订阅名称是否已存在
        SysSubscription nameCheck = subscriptionRepository.findBySubscriptionName(form.getSubscriptionName());
        Assert.isNull(nameCheck, "订阅名称已存在");

        // 校验结束时间必须大于开始时间
        Assert.isTrue(form.getEndDate().isAfter(form.getStartDate()), "结束时间必须大于开始时间");

        SysSubscription subscription = subscriptionMapper.toEntity(form);
        // 设置创建者为当前登录用户
        subscription.setCreateBy(StpUtil.getLoginIdAsString());
        subscriptionRepository.save(subscription);
        return ApiResult.success("新增订阅成功");
    }

    @Override
    public ApiResult<String> update(String id, SysSubscriptionForm form) {
        // 校验订阅 ID 是否存在
        SysSubscription subscription = subscriptionRepository.findById(id).orElse(null);
        Assert.notNull(subscription, "订阅不存在");

        // 校验订阅名称是否已存在
        SysSubscription nameCheck = subscriptionRepository.findBySubscriptionNameAndIdNot(form.getSubscriptionName(), id);
        Assert.isNull(nameCheck, "订阅名称已存在");

        // 校验结束时间必须大于开始时间
        Assert.isTrue(form.getEndDate().isAfter(form.getStartDate()), "结束时间必须大于开始时间");

        subscriptionMapper.updateEntity(form, subscription);
        subscription.setUpdateTime(LocalDateTime.now());
        // 设置更新者为当前登录用户
        subscription.setUpdateBy(StpUtil.getLoginIdAsString());
        subscriptionRepository.save(subscription);
        return ApiResult.success("编辑订阅成功");
    }

    @Override
    public ApiResult<String> delete(String id) {
        // 校验订阅是否存在
        SysSubscription subscription = subscriptionRepository.findById(id).orElse(null);
        Assert.notNull(subscription, "订阅不存在");
        subscriptionRepository.delete(subscription);
        return ApiResult.success("删除订阅成功");
    }

    @Override
    public ApiResult<SysSubscriptionPageVO> getById(String id) {
        SysSubscription subscription = subscriptionRepository.findById(id).orElse(null);
        Assert.notNull(subscription, "订阅不存在");

        SysSubscriptionPageVO vo = subscriptionMapper.toVO(subscription);
        // 计算剩余天数
        vo.setRemainingDays(calculateRemainingDays(subscription.getEndDate()));
        return ApiResult.success(vo);
    }

    @Override
    @DataScope
    public ApiResult<PageData<SysSubscriptionPageVO>> list(SysSubscriptionPageQuery pageQuery) {
        Pageable pageable = PageRequest.of(pageQuery.getPage(), pageQuery.getPageSize());

        // 构建动态查询条件
        Criteria criteria = new Criteria();

        // 应用数据权限过滤(基于 createBy 字段)
        DataScopeHelper.applyDataScopeByCreator(criteria);

        // 根据查询参数动态添加条件
        if (StrUtil.isNotBlank(pageQuery.getSubscriptionName())) {
            criteria.and("subscriptionName").regex(pageQuery.getSubscriptionName(), "i");
        }
        if (pageQuery.getSubscriptionType() != null) {
            criteria.and("subscriptionType").is(pageQuery.getSubscriptionType());
        }
        if (pageQuery.getStatus() != null) {
            criteria.and("status").is(pageQuery.getStatus());
        }

        // 开始时间范围查询
        if (pageQuery.getStartDateStart() != null && pageQuery.getStartDateEnd() != null) {
            criteria.and("startDate").gte(pageQuery.getStartDateStart()).lte(pageQuery.getStartDateEnd());
        } else if (pageQuery.getStartDateStart() != null) {
            criteria.and("startDate").gte(pageQuery.getStartDateStart());
        } else if (pageQuery.getStartDateEnd() != null) {
            criteria.and("startDate").lte(pageQuery.getStartDateEnd());
        }

        // 结束时间范围查询
        if (pageQuery.getEndDateStart() != null && pageQuery.getEndDateEnd() != null) {
            criteria.and("endDate").gte(pageQuery.getEndDateStart()).lte(pageQuery.getEndDateEnd());
        } else if (pageQuery.getEndDateStart() != null) {
            criteria.and("endDate").gte(pageQuery.getEndDateStart());
        } else if (pageQuery.getEndDateEnd() != null) {
            criteria.and("endDate").lte(pageQuery.getEndDateEnd());
        }

        Query query = new Query(criteria);
        // 设置分页和排序
        query.with(pageable).with(Sort.by(Sort.Direction.DESC, "createTime"));
        // 执行查询
        List<SysSubscription> subscriptionList = mongoTemplate.find(query, SysSubscription.class);

        // 查询总数
        long total = mongoTemplate.count(new Query(criteria), SysSubscription.class);

        // 转换为 VO 对象并计算剩余天数
        List<SysSubscriptionPageVO> voList = subscriptionList.stream()
                .map(subscription -> {
                    SysSubscriptionPageVO vo = subscriptionMapper.toVO(subscription);
                    vo.setRemainingDays(calculateRemainingDays(subscription.getEndDate()));
                    return vo;
                })
                .collect(Collectors.toList());

        // 构造 PageData 对象
        PageData<SysSubscriptionPageVO> pageData = new PageData<>();
        pageData.setList(voList);
        pageData.setTotal(total);
        return ApiResult.success(pageData);
    }

    /**
     * 计算剩余天数
     *
     * @param endDate 结束时间
     * @return 剩余天数(负数表示已过期)
     */
    private Integer calculateRemainingDays(LocalDateTime endDate) {
        if (endDate == null) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        long days = ChronoUnit.DAYS.between(now, endDate);
        return (int) days;
    }
}
