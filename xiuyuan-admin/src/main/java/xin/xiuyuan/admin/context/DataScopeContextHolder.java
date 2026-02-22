package xin.xiuyuan.admin.context;

/**
 * 数据权限上下文持有者
 *
 * @author xinbaojian
 * @create 2025-02-22
 */
public class DataScopeContextHolder {

    private static final ThreadLocal<DataScopeContext> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置权限上下文
     *
     * @param context 权限上下文
     */
    public static void set(DataScopeContext context) {
        CONTEXT_HOLDER.set(context);
    }

    /**
     * 获取权限上下文
     *
     * @return 权限上下文
     */
    public static DataScopeContext get() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清理权限上下文
     */
    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
