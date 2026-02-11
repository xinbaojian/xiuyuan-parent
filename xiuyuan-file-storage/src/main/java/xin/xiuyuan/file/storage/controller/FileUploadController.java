package xin.xiuyuan.file.storage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.xiuyuan.common.common.ApiResult;
import xin.xiuyuan.domain.entity.SysAnnex;
import xin.xiuyuan.file.storage.dto.FileInfoVO;
import xin.xiuyuan.file.storage.service.FileStorageService;
import xin.xiuyuan.file.storage.service.ISysAnnexService;

/**
 * 附件管理
 *
 * @author xinbaojian
 * @create 2025-12-31 14:06
 */
@Slf4j
@RestController
@RequestMapping("/file-store/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileStorageService;
    private final ISysAnnexService annexService;

    /**
     * 上传文件
     *
     * @param file              文件
     * @param useRandomFileName 是否使用随机文件名
     * @param overwrite         是否覆盖
     * @return annexId
     */
    @PostMapping(value = "/annex", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "useRandomFileName", required = false, defaultValue = "true") Boolean useRandomFileName,
            @RequestParam(value = "overwrite", required = false, defaultValue = "false") Boolean overwrite
    ) {
        log.info("上传文件: fileName={}, size={}", file.getOriginalFilename(), file.getSize());

        // 上传文件到存储服务
        FileInfoVO fileInfo = fileStorageService.upload(file, useRandomFileName, overwrite);

        // 保存附件记录到数据库
        SysAnnex annex = annexService.createByFileInfo(fileInfo);

        log.info("文件上传成功: annexId={}, objectName={}", annex.getId(), annex.getObjectName());
        return ApiResult.success(annex.getId());
    }

    /**
     * 根据附件ID获取附件信息
     *
     * @param annexId 附件ID
     * @return 附件信息
     */
    @GetMapping("/info/{annexId}")
    public ApiResult<SysAnnex> getAnnexInfo(@PathVariable String annexId) {
        SysAnnex annex = annexService.findById(annexId);
        if (annex == null) {
            return ApiResult.error("附件不存在");
        }
        return ApiResult.success(annex);
    }
}
