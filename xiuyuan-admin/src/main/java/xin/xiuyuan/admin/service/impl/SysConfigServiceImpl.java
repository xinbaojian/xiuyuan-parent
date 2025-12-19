package xin.xiuyuan.admin.service.impl;

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
import xin.xiuyuan.admin.dto.config.SysConfigForm;
import xin.xiuyuan.admin.dto.config.SysConfigPageQuery;
import xin.xiuyuan.admin.entity.SysConfig;
import xin.xiuyuan.admin.mapper.SysConfigMapper;
import xin.xiuyuan.admin.repository.SysConfigRepository;
import xin.xiuyuan.admin.service.ISysConfigService;
import xin.xiuyuan.admin.vo.SysConfigPageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.PageData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统配置 ServiceImpl
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SysConfigServiceImpl implements ISysConfigService {

    private final SysConfigRepository configRepository;

    private final MongoTemplate mongoTemplate;

    private final SysConfigMapper configMapper;


    @Override
    public ApiResult<String> save(SysConfigForm form) {
        // 校验参数键名是否已存在
        SysConfig configKeyCheck = configRepository.findByConfigKey(form.getConfigKey());
        Assert.isNull(configKeyCheck, "参数键名已存在");

        SysConfig config = configMapper.toEntity(form);
        configRepository.save(config);
        return ApiResult.success("新增系统配置成功");
    }

    @Override
    public ApiResult<String> update(String id, SysConfigForm form) {
        // 校验系统配置 ID 是否存在
        SysConfig config = configRepository.findById(id).orElse(null);
        Assert.notNull(config, "系统配置不存在");

        // 校验参数键名是否已存在
        SysConfig configKeyCheck = configRepository.findByConfigKeyAndIdNot(form.getConfigKey(), id);
        Assert.isNull(configKeyCheck, "参数键名已存在");

        configMapper.updateEntity(form, config);
        config.setUpdateTime(LocalDateTime.now());
        configRepository.save(config);
        return ApiResult.success("编辑系统配置成功!");
    }

    @Override
    public ApiResult<String> delete(String id) {
        // 校验系统配置是否存在
        SysConfig config = configRepository.findById(id).orElse(null);
        Assert.notNull(config, "系统配置不存在");
        configRepository.delete(config);
        return ApiResult.success("删除系统配置成功!");
    }

    @Override
    public ApiResult<PageData<SysConfigPageVO>> list(SysConfigPageQuery pageQuery) {
        Pageable pageable = PageRequest.of(pageQuery.getPage(), pageQuery.getPageSize());

        // 构建动态查询条件
        Criteria criteria = new Criteria();

        // 根据查询参数动态添加条件
        if (StrUtil.isNotBlank(pageQuery.getConfigName())) {
            // i 表示忽略大小写
            criteria.and("configName").regex(pageQuery.getConfigName(), "i");
        }
        if (StrUtil.isNotBlank(pageQuery.getConfigKey())) {
            // i 表示忽略大小写
            criteria.and("configKey").regex(pageQuery.getConfigKey(), "i");
        }
        if (pageQuery.getConfigType() != null) {
            criteria.and("configType").is(pageQuery.getConfigType());
        }

        Query query = new Query(criteria);
        // 设置分页
        query.with(pageable).with(Sort.by(Sort.Direction.DESC, "createTime"));
        // 执行查询
        List<SysConfig> configList = mongoTemplate.find(query, SysConfig.class);

        // 查询总数
        long total = mongoTemplate.count(new Query(criteria), SysConfig.class);

        // 转换为 VO 对象
        List<SysConfigPageVO> voList = configList.stream()
                .map(configMapper::toVO)
                .collect(Collectors.toList());

        // 构造 PageData 对象
        PageData<SysConfigPageVO> pageData = new PageData<>();
        pageData.setList(voList);
        pageData.setTotal(total);
        return ApiResult.success(pageData);
    }

    @Override
    public Optional<String> getConfigValue(String key) {
        SysConfig config = configRepository.findByConfigKey(key);
        if (config != null && StrUtil.isNotBlank(config.getConfigValue())) {
            return Optional.of(config.getConfigValue());
        }
        return Optional.empty();
    }
}