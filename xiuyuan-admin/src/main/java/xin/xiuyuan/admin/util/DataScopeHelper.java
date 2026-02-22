package xin.xiuyuan.admin.util;

import cn.hutool.core.collection.CollUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import xin.xiuyuan.admin.context.DataScopeContext;
import xin.xiuyuan.admin.context.DataScopeContextHolder;
import xin.xiuyuan.common.types.DataScopeType;

import java.util.List;

/**
 * 数据权限工具类
 *
 * @author xinbaojian
 * @create 2025-02-22
 */
public class DataScopeHelper {

    /**
     * 应用数据权限过滤（基于部门ID）
     *
     * @param criteria    查询条件
     * @param deptIdField 部门ID字段名
     */
    public static void applyDataScope(Criteria criteria, String deptIdField) {
        applyDataScope(criteria, deptIdField, "id");
    }

    /**
     * 应用数据权限过滤（基于部门ID和用户ID）
     *
     * @param criteria    查询条件
     * @param deptIdField 部门ID字段名
     * @param userIdField 用户ID字段名
     */
    public static void applyDataScope(Criteria criteria, String deptIdField, String userIdField) {
        applyFilter(criteria, deptIdField, userIdField, false);
    }

    /**
     * 应用数据权限过滤（嵌套对象版本，基于部门ID）
     * 例如：查询订单时，需要根据创建用户的部门过滤
     *
     * @param criteria      查询条件
     * @param userDeptField 用户部门ID字段路径（如 "createUser.deptId"）
     * @param userIdField   用户ID字段路径（如 "createUser.id"）
     */
    public static void applyDataScopeForNested(Criteria criteria, String userDeptField, String userIdField) {
        applyFilter(criteria, userDeptField, userIdField, false);
    }

    /**
     * 应用数据权限过滤（基于 createBy 字段）
     * <p>
     * V2.0 新增：适用于业务数据表（文章、订单等）
     * 核心原理：根据创建者所属部门过滤数据，业务表不需要 deptId 字段
     * <p>
     * 示例：
     * 用户A1、A2 属于部门A，用户B1 属于部门B
     * 数据权限为本部门时：
     * - A2 可以看到 A1 创建的数据（同部门）
     * - B1 不能看到 A1 创建的数据（不同部门）
     *
     * @param criteria      查询条件
     * @param createByField 创建者字段名
     */
    public static void applyDataScopeByCreator(Criteria criteria, String createByField) {
        applyFilter(criteria, createByField, createByField, true);
    }

    /**
     * 应用数据权限过滤（基于 createBy 字段，使用默认字段名 "createBy"）
     *
     * @param criteria 查询条件
     */
    public static void applyDataScopeByCreator(Criteria criteria) {
        applyDataScopeByCreator(criteria, "createBy");
    }

    /**
     * 应用数据权限过滤（基于 createBy 字段，嵌套对象版本）
     * <p>
     * V2.0 新增：适用于嵌套对象查询
     * 例如："article.createBy"
     *
     * @param criteria          查询条件
     * @param createByFieldPath 创建者字段路径，如 "article.createBy"
     */
    public static void applyDataScopeByCreatorNested(Criteria criteria, String createByFieldPath) {
        applyFilter(criteria, createByFieldPath, createByFieldPath, true);
    }

    /**
     * 核心过滤逻辑（统一处理所有数据权限过滤）
     *
     * @param criteria        查询条件
     * @param collectionField 集合过滤字段（部门ID字段 或 用户ID字段）
     * @param selfField       仅本人权限时的过滤字段
     * @param useUserIds      true-使用用户ID列表过滤，false-使用部门ID列表过滤
     */
    private static void applyFilter(Criteria criteria, String collectionField, String selfField, boolean useUserIds) {
        DataScopeContext context = DataScopeContextHolder.get();
        if (context == null) {
            return;
        }

        switch (context.getDataScope()) {
            case ALL:
                // 不添加任何限制
                break;

            case DEPT:
            case DEPT_AND_CHILD:
            case CUSTOM:
                // 基于可访问的ID列表过滤
                List<String> accessibleIds = useUserIds ? context.getAccessibleUserIds() : context.getDeptIds();
                if (CollUtil.isNotEmpty(accessibleIds)) {
                    criteria.and(collectionField).in(accessibleIds);
                } else {
                    // 没有可访问的数据，返回空结果
                    criteria.and(collectionField).is("__NO_PERMISSION__");
                }
                break;

            case SELF:
                // 仅本人的数据
                criteria.and(selfField).is(context.getUserId());
                break;
        }
    }

    /**
     * 检查当前用户是否有全部数据权限
     *
     * @return true-有全部权限，false-无全部权限
     */
    public static boolean hasAllDataScope() {
        DataScopeContext context = DataScopeContextHolder.get();
        return context != null && context.getDataScope() == DataScopeType.ALL;
    }

    /**
     * 检查当前用户是否仅本人数据权限
     *
     * @return true-仅本人权限，false-非仅本人权限
     */
    public static boolean isSelfDataScope() {
        DataScopeContext context = DataScopeContextHolder.get();
        return context != null && context.getDataScope() == DataScopeType.SELF;
    }

    /**
     * 获取当前用户可访问的部门ID列表
     *
     * @return 部门ID列表，如果没有限制则返回null
     */
    public static List<String> getAccessibleDeptIds() {
        DataScopeContext context = DataScopeContextHolder.get();
        return context != null ? context.getDeptIds() : null;
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID，如果未登录或无上下文则返回null
     */
    public static String getCurrentUserId() {
        DataScopeContext context = DataScopeContextHolder.get();
        return context != null ? context.getUserId() : null;
    }

    /**
     * 获取当前用户可访问的用户ID列表
     * <p>
     * V2.0 新增：用于基于创建者的数据权限过滤
     *
     * @return 可访问的用户ID列表，如果未登录或无上下文则返回空列表
     */
    public static List<String> getAccessibleUserIds() {
        DataScopeContext context = DataScopeContextHolder.get();
        return context != null ? context.getAccessibleUserIds() : List.of();
    }
}
