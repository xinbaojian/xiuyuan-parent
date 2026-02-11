package xin.xiuyuan.file.storage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xin.xiuyuan.domain.entity.SysAnnex;

import java.util.Optional;

/**
 * 附件数据访问接口
 *
 * @author xinbaojian
 * @create 2025-12-31 14:06
 */
@Repository
public interface SysAnnexRepository extends MongoRepository<SysAnnex, String> {

    /**
     * 根据对象键查询附件
     *
     * @param objectKey 对象键
     * @return 附件记录
     */
    Optional<SysAnnex> findByObjectKey(String objectKey);
}
