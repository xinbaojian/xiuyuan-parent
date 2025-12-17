package xin.xiuyuan.admin.mapper.post;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import xin.xiuyuan.admin.dto.post.SysPostForm;
import xin.xiuyuan.admin.entity.SysPost;
import xin.xiuyuan.admin.vo.post.SysPostPageVO;

/**
 * 岗位 MapStruct 类
 *
 * @author xinbaojian
 * @create 2025-12-17
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface SysPostMapper {

    SysPost toEntity(SysPostForm form);

    void updateEntity(SysPostForm form, @MappingTarget SysPost entity);

    SysPostPageVO toVO(SysPost entity);
}