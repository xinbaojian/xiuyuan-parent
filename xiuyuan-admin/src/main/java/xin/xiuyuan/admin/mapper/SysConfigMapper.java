package xin.xiuyuan.admin.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import xin.xiuyuan.admin.dto.config.SysConfigForm;
import xin.xiuyuan.admin.entity.SysConfig;
import xin.xiuyuan.admin.vo.SysConfigPageVO;

/**
 * 系统配置 Mapper
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface SysConfigMapper {

    /**
     * 将表单转换为实体
     *
     * @param form 表单
     * @return 实体
     */
    SysConfig toEntity(SysConfigForm form);

    /**
     * 将实体更新到目标实体
     *
     * @param form   表单
     * @param entity 目标实体
     */
    void updateEntity(SysConfigForm form, @MappingTarget SysConfig entity);

    /**
     * 将实体转换为页面VO
     *
     * @param entity 实体
     * @return 页面VO
     */
    SysConfigPageVO toVO(SysConfig entity);
}