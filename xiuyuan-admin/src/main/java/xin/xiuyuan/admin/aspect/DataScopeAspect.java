package xin.xiuyuan.admin.aspect;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import xin.xiuyuan.admin.annotation.DataScope;
import xin.xiuyuan.admin.context.DataScopeContext;
import xin.xiuyuan.admin.context.DataScopeContextHolder;
import xin.xiuyuan.admin.service.DataScopeService;

/**
 * 数据权限切面
 *
 * @author xinbaojian
 * @create 2025-02-22
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DataScopeAspect {

    private final DataScopeService dataScopeService;

    @Around("@annotation(dataScope)")
    public Object around(ProceedingJoinPoint point, DataScope dataScope) throws Throwable {
        // 1. 检查是否登录
        if (!StpUtil.isLogin()) {
            return point.proceed();
        }

        // 2. 获取当前登录用户ID
        String userId = StpUtil.getLoginIdAsString();

        // 3. 计算用户的数据权限范围
        DataScopeContext context = dataScopeService.getDataScopeContext(userId);

        if (context == null) {
            return point.proceed();
        }

        // 4. 将权限上下文存入 ThreadLocal
        DataScopeContextHolder.set(context);

        try {
            // 5. 执行原方法
            return point.proceed();
        } finally {
            // 6. 清理 ThreadLocal
            DataScopeContextHolder.clear();
        }
    }
}
