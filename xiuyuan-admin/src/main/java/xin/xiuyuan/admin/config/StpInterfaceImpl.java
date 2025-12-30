package xin.xiuyuan.admin.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xin.xiuyuan.admin.service.ISysUserService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xinbaojian
 * @create 2025-12-19 10:03
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final ISysUserService userService;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        log.info("getPermissionList o: {}, s: {}", o, s);
        List<String> permissionList = new ArrayList<>();
        if (o instanceof String && StrUtil.isNotBlank(o.toString())) {
            permissionList = userService.getPermissionList(o.toString());
        }
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        if (o instanceof String && StrUtil.isNotBlank(o.toString())) {
            List<String> roleKeys = userService.getRoleKeys(o.toString());
            if (CollUtil.isNotEmpty(roleKeys)) {
                return roleKeys;
            }
        }
        return List.of();
    }
}
