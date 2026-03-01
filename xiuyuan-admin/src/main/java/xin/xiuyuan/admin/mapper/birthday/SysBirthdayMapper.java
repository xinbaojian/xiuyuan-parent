package xin.xiuyuan.admin.mapper.birthday;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import xin.xiuyuan.admin.dto.birthday.SysBirthdayForm;
import xin.xiuyuan.admin.entity.SysBirthday;
import xin.xiuyuan.admin.vo.birthday.SysBirthdayPageVO;

/**
 * 生日 MapStruct 类
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface SysBirthdayMapper {

    SysBirthday toEntity(SysBirthdayForm form);

    void updateEntity(SysBirthdayForm form, @MappingTarget SysBirthday entity);

    SysBirthdayPageVO toVO(SysBirthday entity);
}
