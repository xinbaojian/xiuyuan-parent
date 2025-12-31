package xin.xiuyuan.file.storage.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import xin.xiuyuan.file.storage.enums.FileStorageType;

import java.time.Duration;

/**
 * S3 客户端配置
 * 支持 RustFS、MinIO、AWS S3
 *
 * @author xiuyuan
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "file-storage",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class S3ClientConfig {

    private final FileStorageProperties properties;

    /**
     * 创建 S3 客户端 Bean
     */
    @Bean
    @ConditionalOnProperty(
            prefix = "file-storage",
            name = "type",
            havingValue = "rustfs",
            matchIfMissing = false
    )
    public S3Client rustfsS3Client() {
        return createS3Client(FileStorageType.RUSTFS);
    }

    /**
     * 创建 MinIO S3 客户端 Bean
     */
    @Bean
    @ConditionalOnProperty(
            prefix = "file-storage",
            name = "type",
            havingValue = "minio"
    )
    public S3Client minioS3Client() {
        return createS3Client(FileStorageType.MINIO);
    }

    /**
     * 创建 AWS S3 客户端 Bean
     */
    @Bean
    @ConditionalOnProperty(
            prefix = "file-storage",
            name = "type",
            havingValue = "s3"
    )
    public S3Client s3Client() {
        return createS3Client(FileStorageType.S3);
    }

    /**
     * 创建 S3 客户端的通用方法
     */
    private S3Client createS3Client(FileStorageType type) {
        FileStorageProperties.S3Config s3Config = properties.getS3();

        if (s3Config == null) {
            throw new IllegalArgumentException(type.getName() + " 配置不能为空");
        }

        log.info("初始化 {} S3 客户端，端点: {}", type.getName(), s3Config.getEndpoint());

        // 构建凭证
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                s3Config.getAccessKeyId(),
                s3Config.getSecretAccessKey()
        );

        // 构建 S3 客户端（使用 forcePathStyle，这是 RustFS/MinIO 的关键配置）
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(s3Config.getRegion()))
                .endpointOverride(java.net.URI.create(s3Config.getEndpoint()))
                .forcePathStyle(true)  // 关键！RustFS/MinIO 必须启用 Path-Style
                .overrideConfiguration(
                        clientBuilder -> clientBuilder
                                .apiCallTimeout(Duration.ofMillis(s3Config.getReadTimeout()))
                                .apiCallAttemptTimeout(Duration.ofMillis(s3Config.getConnectionTimeout()))
                )
                .build();
    }
}
