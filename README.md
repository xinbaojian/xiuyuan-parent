# 修远管理系统 (Xiuyuan Admin System)

修远管理系统是一个基于Spring Boot开发的企业级后台管理系统，提供了用户管理、部门管理和岗位管理等核心功能模块。

## 项目简介

这是一个现代化的后台管理系统，采用前后端分离架构设计。系统基于Spring Boot框架构建，使用MongoDB作为主数据存储，Redis作为缓存层，提供了完整的RESTful
API接口。

## 技术栈

- **后端框架**: Spring Boot 4.0.0
- **编程语言**: Java 21
- **数据库**: MongoDB
- **缓存**: Redis
- **对象映射**: MapStruct
- **工具库**: Hutool
- **构建工具**: Maven
- **代码简化**: Lombok

## 功能模块

### 1. 用户管理 (User Management)

- 用户信息的增删改查
- 用户状态管理
- 用户类型区分（系统用户、普通用户）
- 用户性别管理

### 2. 部门管理 (Department Management)

- 部门信息的增删改查
- 部门树形结构展示
- 部门状态管理

### 3. 岗位管理 (Position Management)

- 岗位信息的增删改查
- 岗位状态管理

## 项目结构

```
xiuyuan-parent/
├── xiuyuan-admin/          # 后台管理模块
│   ├── controller/         # 控制器层
│   ├── dto/               # 数据传输对象
│   ├── entity/            # 实体类
│   ├── mapper/            # Mapper接口
│   ├── repository/         # 数据访问层
│   ├── service/           # 业务逻辑层
│   └── vo/                # 视图对象
└── xiuyuan-common/         # 公共模块
    ├── common/            # 公共类
    ├── constant/          # 常量定义
    ├── handler/           # 异常处理
    └── types/             # 枚举类型
```

## 环境要求

- Java 21+
- Maven 3.6+
- MongoDB
- Redis

## 配置说明

项目的主要配置在 `xiuyuan-admin/src/main/resources/application.yml` 文件中：

- 服务端口: 8080
- 上下文路径: /api
- MongoDB连接: 配置了连接地址、认证信息等
- Redis连接: 配置了主机、端口、数据库和密码等

## 快速开始

1. 克隆项目到本地
2. 确保已安装Java 21和Maven
3. 配置MongoDB和Redis环境
4. 修改 `application.yml` 中的数据库连接配置
5. 在项目根目录执行以下命令启动项目：
   ```bash
   mvn clean install
   mvn spring-boot:run -pl xiuyuan-admin
   ```

## 开发规范

- 使用Lombok简化Java Bean代码
- 使用MapStruct进行对象映射
- 统一的异常处理机制
- 完整的数据校验机制

## 许可证

本项目仅供学习和参考使用。