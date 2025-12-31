package xin.xiuyuan.admin.vo.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.MenuType;

import java.util.List;

/**
 * 菜单树 VO
 *
 * @author xinbaojian
 * @create 2025-12-29 12:00
 **/
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuTreeVO {

    /**
     * 菜单 ID
     */
    private String id;

    /**
     * 父级菜单 ID
     */
    private String parentId;

    /**
     * 菜单类型
     */
    private MenuType type;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private Object component;

    /**
     * 重定向
     */
    private String redirect;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 是否总是显示在侧边栏
     */
    private Boolean alwaysShow;

    /**
     * 元数据
     */
    private Meta meta;

    /**
     * 菜单状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 是否隐藏
     */
    private Boolean hidden = false;

    /**
     * 显示顺序
     */
    private Integer orderNum = 0;


    /**
     * 子菜单
     */
    private List<MenuTreeVO> children;

    /**
     * 元数据
     */
    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
