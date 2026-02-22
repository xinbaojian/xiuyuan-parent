package xin.xiuyuan.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xin.xiuyuan.admin.context.DataScopeContext;
import xin.xiuyuan.admin.entity.SysDept;
import xin.xiuyuan.admin.entity.SysRole;
import xin.xiuyuan.admin.entity.SysUser;
import xin.xiuyuan.admin.repository.SysDeptRepository;
import xin.xiuyuan.admin.repository.SysRoleRepository;
import xin.xiuyuan.admin.repository.SysUserRepository;
import xin.xiuyuan.admin.service.DataScopeService;
import xin.xiuyuan.common.types.DataScopeType;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据权限服务实现
 *
 * @author xinbaojian
 * @create 2025-02-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataScopeServiceImpl implements DataScopeService {

    private final SysUserRepository userRepository;
    private final SysRoleRepository roleRepository;
    private final SysDeptRepository deptRepository;

    @Override
    @Cacheable(value = "user:dataScope", key = "#userId", unless = "#result == null")
    public DataScopeContext getDataScopeContext(String userId) {
        // 1. 查询用户信息
        SysUser user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        // 2. 查询用户的所有角色
        List<SysRole> roles = List.of();
        if (CollUtil.isNotEmpty(user.getRoleIds())) {
            roles = roleRepository.findAllById(user.getRoleIds());
        }

        // 3. 获取最大的数据权限范围
        DataScopeType maxScope = roles.stream()
                .map(SysRole::getDataScope)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(DataScopeType::getCode))
                .orElse(DataScopeType.SELF);

        // 4. 构建权限上下文
        DataScopeContext context = new DataScopeContext();
        context.setDataScope(maxScope);
        context.setUserId(userId);
        context.setDeptId(user.getDeptId());

        // 5. 根据权限范围计算可访问的用户ID列表（V2.0：基于创建者过滤）
        List<String> accessibleUserIds = calculateAccessibleUserIds(user, maxScope, roles);
        context.setAccessibleUserIds(accessibleUserIds);

        // 6. 根据权限范围计算可访问的部门列表（兼容基于部门ID的过滤）
        switch (maxScope) {
            case ALL:
                // 全部数据，不需要过滤
                break;
            case DEPT:
                if (user.getDeptId() != null) {
                    context.setDeptIds(List.of(user.getDeptId()));
                }
                break;
            case DEPT_AND_CHILD:
                if (user.getDeptId() != null) {
                    // 查询当前部门及所有子部门
                    List<String> childDeptIds = deptRepository
                            .findByAncestorsContaining(user.getDeptId())
                            .stream()
                            .map(SysDept::getId)
                            .collect(Collectors.toList());
                    childDeptIds.add(user.getDeptId()); // 包含当前部门
                    context.setDeptIds(childDeptIds);
                }
                break;
            case CUSTOM:
                // 获取自定义部门列表（取所有角色的自定义部门并集）
                Set<String> customIds = roles.stream()
                        .filter(r -> r.getDataScope() == DataScopeType.CUSTOM)
                        .flatMap(r -> CollUtil.isEmpty(r.getCustomDeptIds())
                                ? java.util.stream.Stream.empty()
                                : r.getCustomDeptIds().stream())
                        .collect(Collectors.toSet());
                context.setDeptIds(List.copyOf(customIds));
                break;
            case SELF:
            default:
                // 仅本人，不需要部门列表
                break;
        }

        return context;
    }

    /**
     * 计算可访问的用户ID列表
     * <p>
     * V2.0 核心逻辑：根据数据权限范围，返回可访问的用户ID列表
     * 用于基于 createBy 字段的数据过滤
     *
     * @param currentUser 当前用户
     * @param scopeType   权限范围类型
     * @param roles       用户角色列表
     * @return 可访问的用户ID列表
     */
    private List<String> calculateAccessibleUserIds(SysUser currentUser, DataScopeType scopeType, List<SysRole> roles) {
        switch (scopeType) {
            case ALL:
                // 全部数据权限：返回所有用户
                return userRepository.findAll()
                        .stream()
                        .map(SysUser::getId)
                        .collect(Collectors.toList());

            case DEPT:
                // 本部门数据权限：返回本部门的所有用户
                if (currentUser.getDeptId() != null) {
                    return userRepository.findByDeptId(currentUser.getDeptId())
                            .stream()
                            .map(SysUser::getId)
                            .collect(Collectors.toList());
                }
                return List.of();

            case DEPT_AND_CHILD:
                // 本部门及子部门数据权限
                if (currentUser.getDeptId() != null) {
                    // 查询当前部门及所有子部门
                    List<SysDept> childDepts = deptRepository
                            .findByAncestorsContaining(currentUser.getDeptId());

                    // 包含当前部门
                    List<String> deptIds = new java.util.ArrayList<>();
                    deptIds.add(currentUser.getDeptId());
                    deptIds.addAll(childDepts.stream()
                            .map(SysDept::getId)
                            .collect(Collectors.toList()));

                    // 查询这些部门的所有用户
                    return userRepository.findByDeptIdIn(deptIds)
                            .stream()
                            .map(SysUser::getId)
                            .collect(Collectors.toList());
                }
                return List.of();

            case CUSTOM:
                // 自定义数据权限：获取自定义部门的所有用户
                List<String> customDeptIds = roles.stream()
                        .filter(r -> r.getDataScope() == DataScopeType.CUSTOM)
                        .flatMap(r -> CollUtil.isEmpty(r.getCustomDeptIds())
                                ? java.util.stream.Stream.empty()
                                : r.getCustomDeptIds().stream())
                        .distinct()
                        .collect(Collectors.toList());

                if (CollUtil.isNotEmpty(customDeptIds)) {
                    return userRepository.findByDeptIdIn(customDeptIds)
                            .stream()
                            .map(SysUser::getId)
                            .collect(Collectors.toList());
                }
                return List.of();

            case SELF:
            default:
                // 仅本人数据权限：只返回当前用户
                return List.of(currentUser.getId());
        }
    }

    @Override
    @CacheEvict(value = "user:dataScope", allEntries = true)
    public void clearDataScopeCache() {
        log.info("清理用户数据权限缓存");
    }
}
