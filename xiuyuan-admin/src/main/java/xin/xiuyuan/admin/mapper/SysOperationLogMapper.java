package xin.xiuyuan.admin.mapper;

import org.mapstruct.Mapper;
import xin.xiuyuan.admin.entity.SysOperationLog;
import xin.xiuyuan.admin.vo.operationlog.SysOperationLogPageVO;

/**
 * 操作日志 Mapper
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        builder = @org.mapstruct.Builder(disableBuilder = true))
public interface SysOperationLogMapper {

    /**
     * 实体转分页VO
     */
    SysOperationLogPageVO toPageVO(SysOperationLog entity);
}
