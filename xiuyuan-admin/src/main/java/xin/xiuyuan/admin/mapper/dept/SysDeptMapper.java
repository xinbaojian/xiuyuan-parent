package xin.xiuyuan.admin.mapper.dept;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import xin.xiuyuan.admin.dto.dept.SysDeptForm;
import xin.xiuyuan.admin.entity.SysDept;
import xin.xiuyuan.admin.vo.dept.SysDeptPageVO;

/**
 * 部门 MapStruct 类
 *
 * @author xinbaojian
 * @create 2025-12-16 09:39
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface SysDeptMapper {

    SysDept toEntity(SysDeptForm form);

    void updateEntity(SysDeptForm form, @MappingTarget SysDept entity);

    SysDeptPageVO toVO(SysDept entity);
}
