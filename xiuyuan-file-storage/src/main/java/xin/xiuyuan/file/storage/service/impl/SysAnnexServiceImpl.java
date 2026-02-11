package xin.xiuyuan.file.storage.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xin.xiuyuan.domain.entity.SysAnnex;
import xin.xiuyuan.file.storage.config.FileStorageProperties;
import xin.xiuyuan.file.storage.dto.FileInfoVO;
import xin.xiuyuan.file.storage.repository.SysAnnexRepository;
import xin.xiuyuan.file.storage.service.ISysAnnexService;

/**
 * 附件服务实现
 *
 * @author xinbaojian
 * @create 2025-12-31 14:06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysAnnexServiceImpl implements ISysAnnexService {

    private final SysAnnexRepository annexRepository;

    private final FileStorageProperties properties;

    @Override
    public SysAnnex createByFileInfo(FileInfoVO fileInfo) {
        SysAnnex annex = new SysAnnex();
        annex.setName(fileInfo.getOriginalFileName());
        annex.setObjectName(fileInfo.getObjectName());
        annex.setBucketName(fileInfo.getBucketName());
        annex.setType(fileInfo.getContentType());
        annex.setSize(String.valueOf(fileInfo.getFileSize()));
        annex.setObjectKey(fileInfo.getObjectName());
        annex.setObjectUrl(fileInfo.getFileUrl());
        return save(annex);
    }

    @Override
    public SysAnnex save(SysAnnex annex) {
        return annexRepository.save(annex);
    }

    @Override
    public SysAnnex findById(String id) {
        SysAnnex annex = annexRepository.findById(id).orElse(null);
        if (annex != null) {
            annex.setObjectUrl(StrUtil.format("{}/{}/{}", properties.getCustomDomain(), properties.getBucketName(), annex.getObjectKey()));
        }
        return annex;
    }

    @Override
    public SysAnnex findByObjectKey(String objectKey) {
        return annexRepository.findByObjectKey(objectKey).orElse(null);
    }
}
