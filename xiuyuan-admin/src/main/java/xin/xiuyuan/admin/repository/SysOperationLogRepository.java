package xin.xiuyuan.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xin.xiuyuan.admin.entity.SysOperationLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志 Repository
 *
 * @author xinbaojian
 * @create 2025-02-22
 **/
@Repository
public interface SysOperationLogRepository extends MongoRepository<SysOperationLog, String> {

    /**
     * 根据操作时间删除日志
     *
     * @param operTime 操作时间(删除此时间之前的记录)
     * @return 删除的记录数
     */
    List<SysOperationLog> deleteByOperTimeBefore(LocalDateTime operTime);
}
