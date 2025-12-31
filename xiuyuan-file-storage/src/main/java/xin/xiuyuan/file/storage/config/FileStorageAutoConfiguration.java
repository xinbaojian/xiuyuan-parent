package xin.xiuyuan.file.storage.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import software.amazon.awssdk.services.s3.S3Client;
import xin.xiuyuan.file.storage.enums.FileStorageType;
import xin.xiuyuan.file.storage.service.FileStorageService;
import xin.xiuyuan.file.storage.service.impl.S3FileStorageServiceImpl;

/**
 * 文件存储自动配置类
 *
 * @author xiuyuan
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(FileStorageProperties.class)
@ConditionalOnProperty(
        prefix = "file-storage",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@ComponentScan(basePackages = "xin.xiuyuan.file.storage")
public class FileStorageAutoConfiguration {

    private final FileStorageProperties properties;
    private final S3Client s3Client;

    public FileStorageAutoConfiguration(FileStorageProperties properties, S3Client s3Client) {
        this.properties = properties;
        this.s3Client = s3Client;
    }

    /**
     * 配置文件存储服务
     * 根据配置的存储类型创建相应的实现
     */
    @Bean
    public FileStorageService fileStorageService() {
        FileStorageType type = properties.getType();

        if (type == null) {
            throw new IllegalStateException("文件存储类型未配置，请设置 file-storage.type");
        }

        log.info("初始化文件存储服务，类型: {}", type.getName());

        // 根据类型返回不同的实现
        // 目前只实现了 S3 协议兼容的服务（RustFS、MinIO、S3）
        return new S3FileStorageServiceImpl(s3Client, properties);
    }
}
