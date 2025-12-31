# Xiuyuan File Storage

文件存储服务模块，支持多种存储服务（RustFS、MinIO、S3、阿里云OSS等）。

## 功能特性

- ✅ **多存储支持**：支持 RustFS、MinIO、AWS S3、阿里云 OSS、本地存储
- ✅ **统一接口**：提供统一的文件存储服务接口，便于切换存储类型
- ✅ **S3 协议兼容**：RustFS、MinIO、S3 使用统一的 AWS S3 SDK 实现
- ✅ **自动配置**：基于 Spring Boot 自动配置，开箱即用
- ✅ **权限控制**：集成 SA-Token 权限控制
- ✅ **可扩展**：设计支持未来添加更多存储类型

## 快速开始

### 1. 添加依赖

在 `xiuyuan-admin` 模块中已经添加了依赖：

```xml
<dependency>
    <groupId>xin.xiuyuan</groupId>
    <artifactId>xiuyuan-file-storage</artifactId>
</dependency>
```

### 2. 配置文件

在 `application.yml` 中添加配置：

```yaml
file-storage:
  enabled: true
  type: rustfs  # 存储类型：rustfs, minio, s3, aliyun-oss, local
  bucket-name: xiuyuan
  base-path: ""
  s3:
    access-key-id: your-access-key-id
    secret-access-key: your-secret-access-key
    endpoint: http://localhost:9000
    region: us-east-1
    path-style-access: true
```

### 3. 使用服务

```java

@Service
@RequiredArgsConstructor
public class YourService {

    private final FileStorageService fileStorageService;

    public void uploadFile(MultipartFile file) {
        FileUploadDTO dto = new FileUploadDTO();
        dto.setBucketName("xiuyuan");
        dto.setPathPrefix("images/2024/");
        dto.setUseRandomFileName(true);

        FileInfoVO fileInfo = fileStorageService.upload(file, dto);
        System.out.println("文件URL: " + fileInfo.getFileUrl());
    }
}
```

## API 接口

所有接口基础路径：`/api/files`

### 上传文件

**简单上传**

```
POST /api/files/upload
Content-Type: multipart/form-data

参数:
- file: 文件
- bucketName: 存储桶名称
- pathPrefix: 路径前缀（可选）
- useRandomFileName: 是否使用随机文件名（默认true）
- overwrite: 是否覆盖（默认false）
```

**带元数据上传**

```
POST /api/files/upload-with-meta
Content-Type: multipart/form-data

参数:
- file: 文件
- FileUploadDTO: JSON格式的上传参数
```

### 下载文件

```
GET /api/files/download/{objectName}
```

### 删除文件

```
DELETE /api/files/{objectName}
```

### 批量删除

```
DELETE /api/files/batch
Content-Type: application/json

Body: ["objectName1", "objectName2", ...]
```

### 获取文件信息

```
GET /api/files/info/{objectName}
```

### 获取文件URL

```
GET /api/files/url/{objectName}
```

### 获取预签名URL

```
GET /api/files/presigned-url?objectName=xxx&expireSeconds=3600
```

### 检查文件是否存在

```
GET /api/files/exists?objectName=xxx
```

## 权限要求

所有接口都需要相应的 SA-Token 权限：

- `file:upload` - 上传文件
- `file:download` - 下载文件
- `file:delete` - 删除文件
- `file:info` - 获取文件信息

## 配置说明

### 存储类型

| 类型         | 说明          | 配置节点                      |
|------------|-------------|---------------------------|
| rustfs     | RustFS 对象存储 | `file-storage.s3`         |
| minio      | MinIO 对象存储  | `file-storage.s3`         |
| s3         | AWS S3      | `file-storage.s3`         |
| aliyun-oss | 阿里云 OSS     | `file-storage.aliyun-oss` |
| local      | 本地存储        | `file-storage.local`      |

### RustFS 配置示例

```yaml
file-storage:
  type: rustfs
  bucket-name: xiuyuan
  s3:
    access-key-id: admin
    secret-access-key: admin123
    endpoint: http://localhost:9000
    region: us-east-1
    path-style-access: true
```

### MinIO 配置示例

```yaml
file-storage:
  type: minio
  bucket-name: xiuyuan
  s3:
    access-key-id: minioadmin
    secret-access-key: minioadmin
    endpoint: http://localhost:9000
    region: us-east-1
    path-style-access: true
```

### AWS S3 配置示例

```yaml
file-storage:
  type: s3
  bucket-name: my-bucket
  s3:
    access-key-id: AKIAIOSFODNN7EXAMPLE
    secret-access-key: wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
    endpoint: https://s3.amazonaws.com
    region: us-west-2
    path-style-access: false
```

## 架构设计

### 模块结构

```
xiuyuan-file-storage/
├── config/              # 配置类
│   ├── FileStorageAutoConfiguration.java
│   ├── FileStorageProperties.java
│   └── S3ClientConfig.java
├── controller/          # 控制器
│   └── FileStorageController.java
├── service/            # 服务接口和实现
│   ├── FileStorageService.java
│   └── impl/
│       └── S3FileStorageServiceImpl.java
├── dto/                # 数据传输对象
│   ├── FileUploadDTO.java
│   ├── FileInfoVO.java
│   └── FileUploadResultVO.java
├── enums/              # 枚举类
│   └── FileStorageType.java
└── exception/          # 异常类
    └── FileStorageException.java
```

### 扩展新的存储类型

1. 在 `FileStorageType` 枚举中添加新类型
2. 实现 `FileStorageService` 接口
3. 创建相应的配置类
4. 在自动配置类中注册 Bean

## 未来拆分为微服务

当需要将文件存储服务拆分为独立的微服务时：

1. 将 `xiuyuan-file-storage` 改为可独立启动的 Spring Boot 应用
2. 添加独立的启动类和配置
3. `xiuyuan-admin` 通过 OpenFeign 或 RestTemplate 调用文件存储服务
4. 或通过 API Gateway 进行路由

## 依赖

- Spring Boot 3.5.9
- AWS S3 SDK 1.12.772
- SA-Token 1.44.0
- Hutool 5.8.42
- Lombok

## License

Apache License 2.0
