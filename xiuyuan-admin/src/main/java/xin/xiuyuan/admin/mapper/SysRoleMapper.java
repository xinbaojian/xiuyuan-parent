package xin.xiuyuan.admin.mapper;

import org.mapstruct.*;
import xin.xiuyuan.admin.dto.role.SysRoleForm;
import xin.xiuyuan.admin.entity.SysRole;
import xin.xiuyuan.admin.vo.SysRolePageVO;

/**
 * 角色 Mapper
 *
 * @author xinbaojian
 * @date 2025-12-17
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface SysRoleMapper {

    /**
     * 将 SysRoleForm 转换为 SysRole 实体
     *
     * @param form 角色表单
     * @return 角色实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    SysRole toEntity(SysRoleForm form);

    /**
     * 将 SysRole 实体转换为 SysRolePageVO
     *
     * @param role 角色实体
     * @return 角色页面VO
     */
    SysRolePageVO toVO(SysRole role);

    /**
     * 将 SysRoleForm 更新到 SysRole 实体
     *
     * @param form 角色表单
     * @param role 角色实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    void updateEntity(SysRoleForm form, @MappingTarget SysRole role);
}