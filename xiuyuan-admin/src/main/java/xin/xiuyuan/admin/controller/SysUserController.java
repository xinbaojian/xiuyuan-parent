package xin.xiuyuan.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.xiuyuan.admin.service.ISysUserService;

/**
 * 用户管理
 *
 * @author xinbaojian
 * @create 2025-12-15 17:35
 **/
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController {

    private final ISysUserService userService;
}
