package xin.xiuyuan.admin.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SaToken 配置
 *
 * @author xinbaojian
 * @create 2025-12-19 10:00
 **/
@Slf4j
@Component
public class SaTokenConfigure implements WebMvcConfigurer {

    /**
     * 不需要登录校验的路径
     */
    private static final String[] EXCLUDE_PATHS = {
            "/login",
            "/logout",
    };

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("注册拦截器");
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATHS)
        ;
    }
}
