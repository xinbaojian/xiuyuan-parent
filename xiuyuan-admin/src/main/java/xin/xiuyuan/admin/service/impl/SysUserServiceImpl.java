package xin.xiuyuan.admin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiuyuan.admin.service.ISysUserService;

/**
 * 用户 ServiceImpl
 *
 * @author xinbaojian
 * @create 2025-12-15 17:34
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements ISysUserService {
}
