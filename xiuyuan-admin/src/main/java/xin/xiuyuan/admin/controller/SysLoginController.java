package xin.xiuyuan.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.xiuyuan.admin.annotation.OperationLog;
import xin.xiuyuan.admin.dto.login.LoginForm;
import xin.xiuyuan.admin.service.ISysUserService;
import xin.xiuyuan.common.common.ApiResult;

/**
 * 登录登出管理
 *
 * @author xinbaojian
 * @create 2025-12-19 11:30
 **/
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SysLoginController {

    private final ISysUserService userService;


    /**
     * 登录
     *
     * @param loginForm 登录表单
     */
    @PostMapping("/login")
    @OperationLog(module = "登录", operationType = "登录", description = "用户登录")
    public ApiResult<?> login(@RequestBody @Validated LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ApiResult.error(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        return userService.login(loginForm, request);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    @OperationLog(module = "登出", operationType = "登出", description = "用户登出")
    public ApiResult<?> logout() {
        return userService.logout();
    }
}
