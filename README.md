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
- **权限框架**: SA-Token
- **AOP**: Spring AOP (用于数据权限切面)
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

### 4. 菜单管理 (Menu Management)

- 菜单信息的增删改查
- 菜单树形结构展示
- 菜单权限配置

### 5. 角色管理 (Role Management)

- 角色信息的增删改查
- 角色权限配置
- **数据权限控制**：支持5种数据权限范围
    - 全部数据权限
    - 本部门数据权限
    - 本部门及子部门数据权限
    - 仅本人数据权限
    - 自定义数据权限

### 6. 数据权限管理 (Data Scope Management)

系统实现了基于角色的数据级权限控制，通过 AOP + 注解的方式实现灵活的数据权限过滤。

#### 5.1 数据权限类型

| 权限类型        | 说明              | 适用场景    |
|-------------|-----------------|---------|
| 全部数据权限      | 可查看所有数据         | 超级管理员   |
| 本部门数据权限     | 只能查看本部门数据       | 部门经理    |
| 本部门及子部门数据权限 | 可查看本部门及所有下级部门数据 | 总公司领导   |
| 仅本人数据权限     | 只能查看自己的数据       | 普通员工    |
| 自定义数据权限     | 可查看指定部门列表的数据    | 跨部门协作人员 |

#### 5.2 多角色权限合并

当用户拥有多个角色时，系统会自动取**最大权限范围**作为用户的最终数据权限。

优先级：全部数据 > 自定义 > 本部门及子部门 > 本部门 > 仅本人

#### 6.3 使用示例

在需要应用数据权限过滤的 Service 方法上添加 `@DataScope` 注解：

```java

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Override
    @DataScope(deptAlias = "u", deptIdField = "deptId")
    public ApiResult<PageData<SysUserPageVO>> list(SysUserPageQuery pageQuery) {
        // 构建查询条件
        Criteria criteria = new Criteria();

        // 应用数据权限过滤（使用工具类，一行代码搞定）
        DataScopeHelper.applyDataScope(criteria, "deptId");

        // ... 其他查询逻辑
    }
}
```

**其他使用场景：**

```java
// 1. 简单场景：只指定部门ID字段名
DataScopeHelper.applyDataScope(criteria, "deptId");

// 2. 自定义字段名：指定部门ID和用户ID字段名
DataScopeHelper.

applyDataScope(criteria, "deptId","userId");

// 3. 嵌套查询：查询订单时，根据创建用户的部门过滤
DataScopeHelper.

applyDataScopeForNested(criteria, "createUser.deptId","createUser.id");

// 4. 判断权限类型
if(DataScopeHelper.

hasAllDataScope()){
        // 有全部数据权限，不添加限制
        }

        if(DataScopeHelper.

isSelfDataScope()){
        // 仅本人数据权限，只查询自己的数据
        }

// 5. 获取可访问的部门列表
List<String> deptIds = DataScopeHelper.getAccessibleDeptIds();
if(CollUtil.

isNotEmpty(deptIds)){
        criteria.

and("deptId").

in(deptIds);
}

// 6. 获取当前用户ID
String userId = DataScopeHelper.getCurrentUserId();
```

#### 5.4 注解参数说明

```java
@DataScope(
        deptAlias = "u",        // 部门表别名（用于多表关联查询）
        deptIdField = "deptId", // 部门ID字段名
        userAlias = "u",        // 用户表别名
        userIdField = "userId"  // 用户ID字段名
)
```

#### 5.5 数据权限配置

在角色管理中配置角色的数据权限范围：

1. 进入角色管理页面
2. 编辑角色，选择"数据权限范围"
3. 如果选择"自定义数据权限"，则需要选择具体的部门列表
4. 保存后，拥有该角色的用户将自动应用相应的数据权限

#### 6.6 性能优化

- **Redis 缓存**：用户的数据权限上下文会被缓存，避免每次查询都计算权限
- **缓存失效**：角色数据权限更新时会自动清理缓存
- **索引优化**：建议为 `deptId` 和 `ancestors` 字段建立索引

#### 6.7 API 使用示例

主要内容包括：

- 创建/编辑角色时配置数据权限
- 查询角色列表（包含数据权限信息）
- 前端表单配置示例
- 数据权限效果验证场景

## 项目结构

```
xiuyuan-parent/
├── xiuyuan-admin/          # 后台管理模块
│   ├── annotation/        # 自定义注解（如 @DataScope）
│   ├── aspect/            # AOP 切面（如 DataScopeAspect）
│   ├── config/            # 配置类
│   ├── context/           # 上下文持有者（如 DataScopeContextHolder）
│   ├── controller/        # 控制器层
│   ├── dto/               # 数据传输对象
│   ├── entity/            # 实体类
│   ├── mapper/            # Mapper接口
│   ├── repository/        # 数据访问层
│   ├── service/           # 业务逻辑层
│   └── vo/                # 视图对象
└── xiuyuan-common/         # 公共模块
    ├── common/            # 公共类
    ├── constant/          # 常量定义
    ├── handler/           # 异常处理
    └── types/             # 枚举类型（如 DataScopeType）
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

### 基础规范

- 使用Lombok简化Java Bean代码
- 使用MapStruct进行对象映射
- 统一的异常处理机制
- 完整的数据校验机制

### 数据权限开发规范

1. **添加数据权限过滤**
    - 在需要数据权限过滤的 Service 方法上添加 `@DataScope` 注解
    - 使用 `DataScopeHelper.applyDataScope()` 工具方法应用权限过滤
    - **不要**在每个 Service 中重复编写权限过滤逻辑

2. **工具类使用**
    - 优先使用 `DataScopeHelper` 静态方法，避免重复代码
    - 根据实际场景选择合适的方法：
        - `applyDataScope(criteria, "deptId")` - 简单场景
        - `applyDataScopeForNested()` - 嵌套对象查询
        - `hasAllDataScope()` - 判断是否有全部权限
        - `getAccessibleDeptIds()` - 获取可访问部门列表

3. **缓存管理**
    - 角色数据权限更新时必须调用 `dataScopeService.clearDataScopeCache()` 清理缓存
    - 避免缓存导致的数据不一致问题

4. **测试要求**
    - 测试各种权限范围场景
    - 测试多角色合并场景
    - 测试边界情况（无部门、无角色等）

## 许可证

本项目仅供学习和参考使用。