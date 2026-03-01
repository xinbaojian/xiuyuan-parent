package xin.xiuyuan.admin.mapper.subscription;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import xin.xiuyuan.admin.dto.subscription.SysSubscriptionForm;
import xin.xiuyuan.admin.entity.SysSubscription;
import xin.xiuyuan.admin.vo.subscription.SysSubscriptionPageVO;

/**
 * 订阅 MapStruct 类
 *
 * @author xiuyuan
 * @create 2025-03-01
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface SysSubscriptionMapper {

    SysSubscription toEntity(SysSubscriptionForm form);

    void updateEntity(SysSubscriptionForm form, @MappingTarget SysSubscription entity);

    SysSubscriptionPageVO toVO(SysSubscription entity);
}
