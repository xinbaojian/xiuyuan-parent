package xin.xiuyuan.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.MenuType;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单权限
 *
 * @author xinbaojian
 * @create 2025-12-29 11:19
 **/
@Data
@Document
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysMenuPermission extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    /**
     * 父级 ID
     */
    private String parentId = "00";

    /**
     * 菜单类型
     */
    private MenuType type = MenuType.MENU;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 重定向
     */
    private String redirect;

    /**
     * 是否总是显示在侧边栏
     */
    private Boolean alwaysShow;

    /**
     * 显示顺序
     */
    private Integer orderNum = 0;

    /**
     * 菜单状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 删除标志
     */
    private Boolean delFlag = false;

    /**
     * 元数据
     */
    private Meta meta;

    /**
     * 元数据
     */
    @Data
    @Accessors(chain = true)
    public static class Meta {

        /**
         * 标题
         */
        private String title;

        /**
         * 图标
         */
        private String icon;

        /**
         * 是否默认展开
         */
        private Boolean defaultOpen;

        /**
         * 权限标识
         */
        private String permissions;
    }
}
