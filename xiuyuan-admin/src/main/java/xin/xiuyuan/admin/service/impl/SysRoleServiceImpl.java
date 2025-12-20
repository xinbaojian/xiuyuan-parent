package xin.xiuyuan.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
import xin.xiuyuan.admin.dto.role.SysRoleForm;
import xin.xiuyuan.admin.dto.role.SysRolePageQuery;
import xin.xiuyuan.admin.entity.SysRole;
import xin.xiuyuan.admin.entity.SysUser;
import xin.xiuyuan.admin.mapper.SysRoleMapper;
import xin.xiuyuan.admin.repository.SysRoleRepository;
import xin.xiuyuan.admin.repository.SysUserRepository;
import xin.xiuyuan.admin.service.ISysRoleService;
import xin.xiuyuan.admin.vo.SysRolePageVO;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.common.common.Option;
import xin.xiuyuan.common.common.PageData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色业务层实现
 *
 * @author xinbaojian
 * @date 2025-12-17
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements ISysRoleService {

    private final SysRoleRepository roleRepository;
    private final MongoTemplate mongoTemplate;
    private final SysRoleMapper roleMapper;

    public SysRoleServiceImpl(SysUserRepository userRepository, SysRoleRepository roleRepository,
                              MongoTemplate mongoTemplate, SysRoleMapper roleMapper) {
        super(userRepository);
        this.roleRepository = roleRepository;
        this.mongoTemplate = mongoTemplate;
        this.roleMapper = roleMapper;
    }


    @Override
    public ApiResult<String> save(SysRoleForm form) {
        // 校验角色权限字符串是否已存在
        SysRole roleKeyCheck = roleRepository.findByRoleKey(form.getRoleKey());
        Assert.isNull(roleKeyCheck, "角色权限字符串已存在");

        // 校验角色名称是否已存在
        SysRole roleNameCheck = roleRepository.findByRoleName(form.getRoleName());
        Assert.isNull(roleNameCheck, "角色名称已存在");

        SysRole role = roleMapper.toEntity(form);
        role.setCreateBy(StpUtil.getLoginIdAsString());
        roleRepository.save(role);
        return ApiResult.success("新增角色成功");
    }

    @Override
    public ApiResult<String> update(String id, SysRoleForm form) {
        // 校验角色 ID 是否存在
        SysRole role = roleRepository.findById(id).orElse(null);
        Assert.notNull(role, "角色不存在");

        // 校验角色权限字符串是否已存在
        SysRole roleKeyCheck = roleRepository.findByRoleKeyAndIdNot(form.getRoleKey(), id);
        Assert.isNull(roleKeyCheck, "角色权限字符串已存在");

        // 校验角色名称是否已存在
        SysRole roleNameCheck = roleRepository.findByRoleNameAndIdNot(form.getRoleName(), id);
        Assert.isNull(roleNameCheck, "角色名称已存在");

        roleMapper.updateEntity(form, role);
        role.setUpdateTime(LocalDateTime.now());
        role.setUpdateBy(StpUtil.getLoginIdAsString());
        roleRepository.save(role);
        return ApiResult.success("编辑角色成功!");
    }

    @Override
    public ApiResult<String> delete(String id) {
        // 校验角色是否存在
        SysRole role = roleRepository.findById(id).orElse(null);
        Assert.notNull(role, "角色不存在");
        //TODO 需要校验是否有用户关联，如果有则不允许删除
        roleRepository.delete(role);
        return ApiResult.success("删除角色成功!");
    }

    @Override
    public ApiResult<PageData<SysRolePageVO>> list(SysRolePageQuery pageQuery) {
        Pageable pageable = PageRequest.of(pageQuery.getPage(), pageQuery.getPageSize());

        // 构建动态查询条件
        Criteria criteria = new Criteria();

        // 根据查询参数动态添加条件
        if (StrUtil.isNotBlank(pageQuery.getRoleKey())) {
            // i 表示忽略大小写
            criteria.and("roleKey").regex(pageQuery.getRoleKey(), "i");
        }
        if (StrUtil.isNotBlank(pageQuery.getRoleName())) {
            // i 表示忽略大小写
            criteria.and("roleName").regex(pageQuery.getRoleName(), "i");
        }
        if (pageQuery.getStatus() != null) {
            criteria.and("status").is(pageQuery.getStatus());
        }

        Query query = new Query(criteria);
        // 设置分页
        query.with(pageable).with(Sort.by(Sort.Direction.ASC, "orderNum"));
        // 执行查询
        List<SysRole> roleList = mongoTemplate.find(query, SysRole.class);
        // 查询总数
        long total = mongoTemplate.count(new Query(criteria), SysRole.class);
        // 转换为 VO 对象
        List<SysRolePageVO> voList = roleList.stream()
                .map(roleMapper::toVO)
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(voList)) {
            Map<String, SysUser> userMap = super.getUserMap(roleList);
            if (!userMap.isEmpty()) {
                voList.forEach(vo -> {
                    if (StrUtil.isNotBlank(vo.getCreateBy()) && userMap.containsKey(vo.getCreateBy()))
                        vo.setCreateByName(userMap.get(vo.getCreateBy()).getUsername());
                    if (StrUtil.isNotBlank(vo.getUpdateBy()) && userMap.containsKey(vo.getUpdateBy()))
                        vo.setUpdateByName(userMap.get(vo.getUpdateBy()).getUsername());
                });
            }
        }
        // 构造 PageData 对象
        PageData<SysRolePageVO> pageData = new PageData<>();
        pageData.setList(voList);
        pageData.setTotal(total);
        return ApiResult.success(pageData);
    }

    @Override
    public ApiResult<List<Option>> options() {
        List<Option> options = roleRepository.findAll().stream()
                .map(role -> new Option().setLabel(role.getRoleName()).setValue(role.getId()))
                .toList();
        return ApiResult.success(options);
    }
}