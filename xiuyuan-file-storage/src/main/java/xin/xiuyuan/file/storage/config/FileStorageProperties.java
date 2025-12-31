package xin.xiuyuan.file.storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import xin.xiuyuan.file.storage.enums.FileStorageType;

/**
 * 文件存储配置属性
 *
 * @author xiuyuan
 */
@Data
@ConfigurationProperties(prefix = "file-storage")
public class FileStorageProperties {

    /**
     * 是否启用文件存储服务
     */
    private Boolean enabled = true;

    /**
     * 存储类型（rustfs, minio, s3, aliyun-oss, local）
     */
    private FileStorageType type;

    /**
     * 默认存储桶名称
     */
    private String bucketName = "xiuyuan";

    /**
     * 基础路径（用于生成对象名）
     */
    private String basePath = "";

    /**
     * 自定义域名（用于访问文件）
     */
    private String customDomain;

    /**
     * S3 协议兼容配置（RustFS、MinIO、S3 通用）
     */
    private S3Config s3;

    /**
     * 本地存储配置
     */
    private LocalConfig local;

    /**
     * 阿里云 OSS 配置
     */
    private AliyunOssConfig aliyunOss;

    /**
     * S3 协议兼容配置
     */
    @Data
    public static class S3Config {
        /**
         * 访问密钥 ID
         */
        private String accessKeyId;

        /**
         * 访问密钥 Secret
         */
        private String secretAccessKey;

        /**
         * 服务端点（如：http://localhost:9000）
         */
        private String endpoint;

        /**
         * 区域（如：us-east-1）
         */
        private String region = "us-east-1";

        /**
         * 连接超时时间（毫秒）
         */
        private Integer connectionTimeout = 10000;

        /**
         * 读取超时时间（毫秒）
         */
        private Integer readTimeout = 60000;
    }

    /**
     * 本地存储配置
     */
    @Data
    public static class LocalConfig {
        /**
         * 存储根路径
         */
        private String rootPath = "./storage";

        /**
         * 访问基础URL
         */
        private String baseUrl = "/files";
    }

    /**
     * 阿里云 OSS 配置
     */
    @Data
    public static class AliyunOssConfig {
        /**
         * 访问密钥 ID
         */
        private String accessKeyId;

        /**
         * 访问密钥 Secret
         */
        private String accessKeySecret;

        /**
         * 端点（如：oss-cn-hangzhou.aliyuncs.com）
         */
        private String endpoint;

        /**
         * 存储桶名称
         */
        private String bucketName;
    }
}
