package xin.xiuyuan.admin.mapper.menu;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import xin.xiuyuan.admin.dto.menu.MenuPermissionForm;
import xin.xiuyuan.admin.entity.SysMenuPermission;

/**
 * 菜单权限 MapStruct 类
 *
 * @author xinbaojian
 * @create 2025-12-29 11:33
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface MenuPermissionMapper {

    /**
     * 表单转实体
     *
     * @param form 表单
     * @return 实体
     */
    SysMenuPermission toEntity(MenuPermissionForm form);

    /**
     * 更新实体
     *
     * @param form   表单
     * @param entity 实体
     */
    void updateEntity(MenuPermissionForm form, @MappingTarget SysMenuPermission entity);
}
