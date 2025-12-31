package xin.xiuyuan.file.storage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件存储类型枚举
 *
 * @author xiuyuan
 */
@Getter
@AllArgsConstructor
public enum FileStorageType {

    /**
     * RustFS 存储服务
     */
    RUSTFS("rustfs", "RustFS"),

    /**
     * MinIO 存储服务
     */
    MINIO("minio", "MinIO"),

    /**
     * AWS S3 存储服务
     */
    S3("s3", "AWS S3"),

    /**
     * 阿里云 OSS 存储服务
     */
    ALIYUN_OSS("aliyun-oss", "阿里云OSS"),

    /**
     * 本地存储
     */
    LOCAL("local", "本地存储");

    /**
     * 存储类型代码
     */
    private final String code;

    /**
     * 存储类型名称
     */
    private final String name;

    /**
     * 根据代码获取存储类型
     *
     * @param code 存储类型代码
     * @return 存储类型枚举
     */
    public static FileStorageType fromCode(String code) {
        for (FileStorageType type : values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("不支持的文件存储类型: " + code);
    }
}
