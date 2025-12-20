package xin.xiuyuan.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import xin.xiuyuan.admin.entity.BaseEntity;
import xin.xiuyuan.admin.entity.SysUser;
import xin.xiuyuan.admin.repository.SysUserRepository;
import xin.xiuyuan.admin.service.IBaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 公共 Service
 *
 * @author xinbaojian
 * @create 2025-12-20 11:44
 **/
@Service
public class BaseServiceImpl<T extends BaseEntity> implements IBaseService<T> {

    private final SysUserRepository userRepository;

    public BaseServiceImpl(SysUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, SysUser> getUserMap(List<T> entityList) {
        if (CollUtil.isEmpty(entityList)) {
            return Map.of();
        }
        List<String> userIdList =
                entityList.stream()
                        .map(entity -> {
                            List<String> ids = new ArrayList<>();
                            if (StrUtil.isNotBlank(entity.getCreateBy())) {
                                ids.add(entity.getCreateBy());
                            }
                            if (StrUtil.isNotBlank(entity.getUpdateBy())) {
                                ids.add(entity.getUpdateBy());
                            }
                            return ids;
                        })
                        .flatMap(List::stream)
                        .distinct()
                        .toList();
        if (CollUtil.isEmpty(userIdList)) {
            return Map.of();
        }
        List<SysUser> userList = userRepository.findAllById(userIdList);
        // 转换成 Map, key 为 ID, value 为用户信息
        return userList.stream()
                .collect(Collectors.toMap(SysUser::getId, user -> user));
    }
}