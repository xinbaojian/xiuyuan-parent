package xin.xiuyuan.common.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xin.xiuyuan.common.common.ApiResult;

/**
 * 全局异常拦截器
 *
 * @author xinbaojian
 * @create 2025-12-16 10:55
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 非法参数异常
     *
     * @param e IllegalArgumentException
     * @return ApiResult
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<?> handlerIllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("非法参数异常", e);
        ApiResult<?> result = ApiResult.error(StrUtil.isBlank(e.getMessage()) ? "非法参数" : e.getMessage());
        result.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    /**
     * 参数解析失败
     *
     * @param e HttpMessageNotReadableException
     * @return ApiResult
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<?> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        ApiResult<?> result = ApiResult.error("参数解析失败");
        result.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    /**
     * 未登录异常
     *
     * @param e NotLoginException
     * @return ApiResult
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public ResponseEntity<?> handlerNotLoginException(NotLoginException e) {
        log.error("未登录", e);
        ApiResult<?> result = ApiResult.error("未登录");
        result.setCode(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}