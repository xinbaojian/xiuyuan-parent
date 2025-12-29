package xin.xiuyuan.admin.dto.menu;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import xin.xiuyuan.common.types.CommonStatus;
import xin.xiuyuan.common.types.MenuType;

/**
 * 菜单权限表单 DTO
 *
 * @author xinbaojian
 * @create 2025-12-29 11:30
 **/
@Data
@Accessors(chain = true)
public class MenuPermissionForm {

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
    @NotBlank(message = "路由路径不能为空")
    private String path;

    /**
     * 路由名称
     */
    @NotBlank(message = "路由名称不能为空")
    private String name;

    /**
     * 组件路径
     */
    private String component = "Layout";

    /**
     * 重定向
     */
    private String redirect = "noRedirect";

    /**
     * 是否总是显示在侧边栏
     */
    private Boolean alwaysShow = false;

    /**
     * 显示顺序
     */
    private Integer orderNum = 0;

    /**
     * 菜单状态
     */
    private CommonStatus status = CommonStatus.NORMAL;

    /**
     * 标题
     */
    @NotBlank(message = "菜单标题不能为空")
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否默认展开
     */
    private Boolean defaultOpen = false;

    /**
     * 权限标识
     */
    private String permissions;
}
