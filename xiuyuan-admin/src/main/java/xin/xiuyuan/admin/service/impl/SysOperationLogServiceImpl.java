package xin.xiuyuan.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import xin.xiuyuan.admin.annotation.DataScope;
import xin.xiuyuan.admin.dto.operationlog.SysOperationLogPageQuery;
import xin.xiuyuan.admin.entity.SysOperationLog;
import xin.xiuyuan.admin.entity.SysUser;
import xin.xiuyuan.admin.mapper.SysOperationLogMapper;
import xin.xiuyuan.admin.repository.SysOperationLogRepository;
import xin.xiuyuan.admin.repository.SysUserRepository;
import xin.xiuyuan.admin.service.ISysConfigService;
import xin.xiuyuan.admin.service.ISysOperationLogService;
import xin.xiuyuan.admin.util.DataScopeHelper;
import xin.xiuyuan.admin.vo.operationlog.SysOperationLogPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;
import xin.xiuyuan.common.constant.ConfigConstant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 操作日志 ServiceImpl
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
@Slf4j
@Service
public class SysOperationLogServiceImpl extends BaseServiceImpl<SysOperationLog> implements ISysOperationLogService {

    private final SysOperationLogRepository repository;
    private final MongoTemplate mongoTemplate;
    private final SysOperationLogMapper mapper;
    private final SysUserRepository userRepository;
    private final ISysConfigService configService;

    public SysOperationLogServiceImpl(SysUserRepository userRepository,
                                      SysOperationLogRepository repository,
                                      MongoTemplate mongoTemplate,
                                      SysOperationLogMapper mapper,
                                      ISysConfigService configService) {
        super(userRepository);
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.configService = configService;
    }

    @Override
    @DataScope(deptAlias = "u")
    public ApiResult<PageData<SysOperationLogPageVO>> list(SysOperationLogPageQuery pageQuery) {
        // 构建查询条件
        Criteria criteria = buildCriteria(pageQuery);

        // 分页和排序
        Pageable pageable = PageRequest.of(
                pageQuery.getPage(),
                pageQuery.getPageSize(),
                Sort.by(Sort.Direction.DESC, "operTime")
        );

        // 执行查询
        Query query = new Query(criteria).with(pageable);
        List<SysOperationLog> list = mongoTemplate.find(query, SysOperationLog.class);
        long total = mongoTemplate.count(query, SysOperationLog.class);

        // 转换VO
        List<SysOperationLogPageVO> voList = list.stream()
                .map(mapper::toPageVO)
                .collect(Collectors.toList());

        return ApiResult.success(new PageData<>(voList, total));
    }

    @Override
    public ApiResult<SysOperationLogPageVO> findById(String id) {
        return repository.findById(id)
                .map(mapper::toPageVO)
                .map(ApiResult::success)
                .orElse(ApiResult.error("日志不存在"));
    }

    @Override
    public Map<String, SysUser> getUserMap(List<SysOperationLog> entityList) {
        // 实现IBaseService接口方法
        List<String> userIds = entityList.stream()
                .map(SysOperationLog::getOperatorId)
                .filter(StrUtil::isNotBlank)
                .distinct()
                .collect(Collectors.toList());

        if (userIds.isEmpty()) {
            return Map.of();
        }

        return userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(SysUser::getId, user -> user));
    }

    @Override
    public void cleanExpiredLogs() {
        try {
            // 获取保留天数配置
            int retentionDays = configService.getConfigValue(ConfigConstant.SYS_OPERATION_LOG_RETENTION_DAYS)
                    .map(Integer::parseInt)
                    .orElse(90);

            // 小于等于0表示永久保留
            if (retentionDays <= 0) {
                log.info("操作日志保留时间配置为永久保留,不执行清理");
                return;
            }

            // 计算截止时间
            LocalDateTime cutoffTime = LocalDateTime.now().minusDays(retentionDays);

            // 删除过期日志
            List<SysOperationLog> deletedLogs = repository.deleteByOperTimeBefore(cutoffTime);
            log.info("清理过期操作日志完成, 删除记录数: {}", deletedLogs.size());
        } catch (Exception e) {
            log.error("清理过期操作日志失败", e);
        }
    }

    /**
     * 构建查询条件
     */
    private Criteria buildCriteria(SysOperationLogPageQuery pageQuery) {
        Criteria criteria = new Criteria();

        // 应用数据权限过滤
        // 操作日志的数据权限基于：
        // 1. operatorId（操作人ID）- 用于"仅本人"权限
        // 2. deptId（操作人部门ID）- 用于"本部门"、"本部门及以下"、"自定义"权限
        DataScopeHelper.applyDataScope(criteria, "deptId", "operatorId");

        if (StrUtil.isNotBlank(pageQuery.getModule())) {
            criteria.and("module").is(pageQuery.getModule());
        }

        if (StrUtil.isNotBlank(pageQuery.getOperationType())) {
            criteria.and("operationType").is(pageQuery.getOperationType());
        }

        if (StrUtil.isNotBlank(pageQuery.getOperatorId())) {
            criteria.and("operatorId").is(pageQuery.getOperatorId());
        }

        if (StrUtil.isNotBlank(pageQuery.getOperatorName())) {
            criteria.and("operatorName").regex(pageQuery.getOperatorName());
        }

        if (pageQuery.getStatus() != null) {
            criteria.and("status").is(pageQuery.getStatus());
        }

        if (pageQuery.getStartTime() != null && pageQuery.getEndTime() != null) {
            criteria.and("operTime").gte(pageQuery.getStartTime()).lte(pageQuery.getEndTime());
        } else if (pageQuery.getStartTime() != null) {
            criteria.and("operTime").gte(pageQuery.getStartTime());
        } else if (pageQuery.getEndTime() != null) {
            criteria.and("operTime").lte(pageQuery.getEndTime());
        }

        return criteria;
    }
}
