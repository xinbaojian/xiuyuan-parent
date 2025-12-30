package xin.xiuyuan.admin.mapper;

import org.mapstruct.*;
import xin.xiuyuan.admin.dto.user.SysUserForm;
import xin.xiuyuan.admin.entity.SysUser;
import xin.xiuyuan.admin.vo.SysUserPageVO;

/**
 * 用户 Mapper
 *
 * @author xinbaojian
 * @create 2025-12-19
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface SysUserMapper {

    /**
     * 将 SysUserForm 转换为 SysUser 实体
     *
     * @param form 用户表单
     * @return 用户实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "loginIp", ignore = true)
    @Mapping(target = "loginDate", ignore = true)
    @Mapping(target = "pwdUpdateDate", ignore = true)
    @Mapping(target = "salt", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "deptId", ignore = true)
    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "roleIds", ignore = true)
    SysUser toEntity(SysUserForm form);

    /**
     * 将 SysUser 实体转换为 SysUserPageVO
     *
     * @param user 用户实体
     * @return 用户页面VO
     */
    SysUserPageVO toVO(SysUser user);

    /**
     * 将 SysUserForm 更新到 SysUser 实体
     *
     * @param form 用户表单
     * @param user 用户实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "loginIp", ignore = true)
    @Mapping(target = "loginDate", ignore = true)
    @Mapping(target = "pwdUpdateDate", ignore = true)
    @Mapping(target = "salt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "deptId", ignore = true)
    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "roleIds", ignore = true)
    void updateEntity(SysUserForm form, @MappingTarget SysUser user);
}