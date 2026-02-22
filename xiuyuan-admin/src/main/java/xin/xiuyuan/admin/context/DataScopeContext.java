package xin.xiuyuan.admin.context;

import lombok.Data;
import xin.xiuyuan.common.types.DataScopeType;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限上下文
 *
 * @author xinbaojian
 * @create 2025-02-22
 */
@Data
public class DataScopeContext {

    /**
     * 数据权限范围
     */
    private DataScopeType dataScope;

    /**
     * 当前用户ID
     */
    private String userId;

    /**
     * 当前用户部门ID
     */
    private String deptId;

    /**
     * 可访问的部门ID列表
     */
    private List<String> deptIds = new ArrayList<>();

    /**
     * 可访问的用户ID列表（基于部门计算得出）
     */
    private List<String> accessibleUserIds = new ArrayList<>();

    /**
     * 是否是超级管理员
     */
    private Boolean isAdmin = false;
}
