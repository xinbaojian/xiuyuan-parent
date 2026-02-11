package xin.xiuyuan.file.storage.service;

import xin.xiuyuan.domain.entity.SysAnnex;
import xin.xiuyuan.file.storage.dto.FileInfoVO;

/**
 * 附件服务接口
 *
 * @author xinbaojian
 * @create 2025-12-31 14:06
 */
public interface ISysAnnexService {

    /**
     * 根据文件信息创建附件记录
     *
     * @param fileInfo 文件信息
     * @return 附件记录
     */
    SysAnnex createByFileInfo(FileInfoVO fileInfo);

    /**
     * 保存附件记录
     *
     * @param annex 附件记录
     * @return 保存后的附件记录
     */
    SysAnnex save(SysAnnex annex);

    /**
     * 根据ID查询附件
     *
     * @param id 附件ID
     * @return 附件记录
     */
    SysAnnex findById(String id);

    /**
     * 根据objectKey查询附件
     *
     * @param objectKey 对象键
     * @return 附件记录
     */
    SysAnnex findByObjectKey(String objectKey);
}
