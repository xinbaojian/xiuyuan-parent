package xin.xiuyuan.file.storage.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xin.xiuyuan.domain.entity.SysAnnex;
import xin.xiuyuan.file.storage.dto.FileInfoVO;
import xin.xiuyuan.file.storage.service.ISysAnnexService;

/**
 * 文件存储禁用时的空实现
 * <p>
 * 当 file-storage.enabled=false 时，此类会被注册为 ISysAnnexService 的实现。
 * 所有方法返回 null 或不做任何操作，并记录警告日志。
 *
 * @author xiuyuan
 * @create 2025-03-01
 */
@Slf4j
@Service
@ConditionalOnProperty(
        prefix = "file-storage",
        name = "enabled",
        havingValue = "false"
)
public class SysAnnexServiceDisabledImpl implements ISysAnnexService {

    @Override
    public SysAnnex createByFileInfo(FileInfoVO fileInfo) {
        log.warn("文件存储功能已禁用，无法创建附件记录");
        return null;
    }

    @Override
    public SysAnnex save(SysAnnex annex) {
        log.warn("文件存储功能已禁用，无法保存附件记录");
        return null;
    }

    @Override
    public SysAnnex findById(String id) {
        log.warn("文件存储功能已禁用，无法查询附件记录");
        return null;
    }

    @Override
    public SysAnnex findByObjectKey(String objectKey) {
        log.warn("文件存储功能已禁用，无法查询附件记录");
        return null;
    }
}
