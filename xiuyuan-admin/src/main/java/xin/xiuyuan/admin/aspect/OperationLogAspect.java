package xin.xiuyuan.admin.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xin.xiuyuan.admin.annotation.OperationLog;
import xin.xiuyuan.admin.entity.SysDept;
import xin.xiuyuan.admin.entity.SysOperationLog;
import xin.xiuyuan.admin.entity.SysUser;
import xin.xiuyuan.admin.repository.SysDeptRepository;
import xin.xiuyuan.admin.repository.SysOperationLogRepository;
import xin.xiuyuan.admin.repository.SysUserRepository;
import xin.xiuyuan.common.types.OperationStatus;
import xin.xiuyuan.common.utils.SensitiveDataUtil;

import java.time.LocalDateTime;

/**
 * 操作日志切面
 *
 * @author xinbaojian
 * @create 2025-02-22
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final SysOperationLogRepository operationLogRepository;
    private final SysUserRepository userRepository;
    private final SysDeptRepository deptRepository;
    private final ObjectMapper objectMapper;

    /**
     * 环绕通知: 拦截@OperationLog注解的方法
     */
    @Around("@annotation(operationLog)")
    public Object around(ProceedingJoinPoint point, OperationLog operationLog) throws Throwable {
        long startTime = System.currentTimeMillis();
        OperationStatus status = OperationStatus.SUCCESS;
        String errorMsg = null;
        Object result = null;

        try {
            // 执行业务方法
            result = point.proceed();
            return result;
        } catch (Exception e) {
            status = OperationStatus.FAILURE;
            errorMsg = e.getMessage();
            throw e;
        } finally {
            long costTime = System.currentTimeMillis() - startTime;
            // 异步记录日志
            saveOperationLog(point, operationLog, result, status, errorMsg, costTime);
        }
    }

    /**
     * 异步保存操作日志
     */
    @Async
    public void saveOperationLog(ProceedingJoinPoint point, OperationLog operationLog,
                                 Object result, OperationStatus status, String errorMsg, long costTime) {
        try {
            // 获取Request
            HttpServletRequest request = getRequest();
            if (request == null) {
                return;
            }

            // 构建日志实体
            SysOperationLog logEntity = new SysOperationLog();

            // 基本信息
            logEntity.setModule(operationLog.module());
            logEntity.setOperationType(operationLog.operationType());

            // 描述处理
            String description = StrUtil.isNotBlank(operationLog.description())
                    ? operationLog.description()
                    : operationLog.operationType() + operationLog.module();
            logEntity.setDescription(description);

            // 操作人信息
            if (StpUtil.isLogin()) {
                String userId = StpUtil.getLoginIdAsString();
                logEntity.setOperatorId(userId);
                logEntity.setCreateBy(userId);

                // 查询用户详细信息
                SysUser user = userRepository.findById(userId).orElse(null);
                if (user != null) {
                    logEntity.setOperatorName(user.getUsername());
                    logEntity.setDeptId(user.getDeptId());

                    // 查询部门名称
                    if (StrUtil.isNotBlank(user.getDeptId())) {
                        SysDept dept = deptRepository.findById(user.getDeptId()).orElse(null);
                        if (dept != null) {
                            logEntity.setDeptName(dept.getDeptName());
                        }
                    }
                }
            }

            // 请求信息
            MethodSignature signature = (MethodSignature) point.getSignature();
            logEntity.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
            logEntity.setRequestMethod(request.getMethod());
            logEntity.setRequestUrl(request.getRequestURI());
            logEntity.setOperIp(getIpAddr(request));
            // TODO: IP地址解析为地理位置(可选功能)

            // 参数和结果
            if (operationLog.recordParams()) {
                String params = filterSensitiveParams(point.getArgs(), operationLog.sensitiveFields());
                logEntity.setOperParam(params);
            }

            if (operationLog.recordResult() && result != null) {
                logEntity.setJsonResult(toJsonString(result));
            }

            // 执行信息
            logEntity.setStatus(status);
            logEntity.setErrorMsg(errorMsg);
            logEntity.setCostTime((int) costTime);
            logEntity.setOperTime(LocalDateTime.now());

            // 保存
            operationLogRepository.save(logEntity);
        } catch (Exception e) {
            log.error("保存操作日志失败", e);
        }
    }

    /**
     * 过滤敏感参数
     */
    private String filterSensitiveParams(Object[] args, String[] sensitiveFields) {
        return SensitiveDataUtil.filterSensitiveParams(args, sensitiveFields);
    }

    /**
     * 对象转JSON字符串
     */
    private String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "对象序列化失败";
        }
    }

    /**
     * 获取当前请求
     */
    private HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes != null ? attributes.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取客户端IP地址
     */
    private String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于多个代理的情况，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
