package xin.xiuyuan.common.common;

import lombok.Data;

/**
 * 统一返回结构
 *
 * @author xinbaojian
 * @create 2025-12-16 10:46
 **/
@Data
public class ApiResult<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(200);
        result.setMessage("成功");
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> success() {
        return success(null);
    }

    public static <T> ApiResult<T> error(Integer code, String message) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> ApiResult<T> error(String message) {
        return error(500, message);
    }

    public static <T> ApiResult<T> error() {
        return error(500, "服务器异常");
    }


}
