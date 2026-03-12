# Spring Boot 矿山检测系统后端 - 开发指南

## 项目概述

这是一个基于 Spring Boot 3.2 和 MySQL 8.0 的矿山企业管理及污染监测系统的后端 API 服务。

**项目地址：**/mining-backend

## 快速开始

### 环境要求

- Java 17 或更高版本
- Maven 3.8 或更高版本
- MySQL 8.0 或更高版本
- IDE 推荐：IntelliJ IDEA 或 VS Code

### 数据库配置

1. **创建数据库**

```sql
# 方式一：使用 SQL 脚本
mysql -u root -p < init-db.sql

# 方式二：在 MySQL 客户端运行
CREATE DATABASE mining_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. **修改应用配置**

编辑 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mining_db?useSSL=false&serverTimezone=UTC
    username: root        # 改为你的 MySQL 用户名
    password: root        # 改为你的 MySQL 密码
```

### 编译和运行

```bash
# 编译项目
mvn clean compile

# 运行应用
mvn spring-boot:run

# 或者构建后运行
mvn clean package
java -jar target/mining-detection-system-1.0.0.jar
```

应用启动后访问 `http://localhost:8080`

## 项目架构

### 分层结构

- **Controller** - REST API 端点，处理 HTTP 请求和响应
- **Service** - 业务逻辑层，处理核心业务逻辑
- **Repository** - 数据访问层，与数据库交互
- **Entity** - JPA 实体，映射数据库表

### 核心功能模块

1. **矿企管理** (`MineEnterprise`)
   - 企业基本信息管理
   - 企业状态管理

2. **矿产管理** (`Mineral`)
   - 矿产信息记录
   - 储量和品位管理
   - 开采状态跟踪

3. **员工管理** (`Employee`)
   - 员工信息管理
   - 部门和职位管理
   - 员工分配到企业

4. **污染数据** (`PollutionData`)
   - 实时污染数据记录
   - 污染指数和等级划分

## API 端点

### 矿企管理API
- `GET /api/v1/enterprises` - 获取所有矿企
- `POST /api/v1/enterprises` - 创建矿企
- `PUT /api/v1/enterprises/{id}` - 更新矿企
- `DELETE /api/v1/enterprises/{id}` - 删除矿企

### 矿产管理API
- `GET /api/v1/minerals` - 获取所有矿产
- `POST /api/v1/minerals` - 创建矿产
- `PUT /api/v1/minerals/{id}` - 更新矿产
- `DELETE /api/v1/minerals/{id}` - 删除矿产

### 员工管理API
- `GET /api/v1/employees` - 获取所有员工
- `POST /api/v1/employees` - 创建员工
- `PUT /api/v1/employees/{id}` - 更新员工
- `DELETE /api/v1/employees/{id}` - 删除员工

### 污染数据API
- `GET /api/v1/pollution` - 获取污染数据
- `POST /api/v1/pollution` - 记录污染数据
- `GET /api/v1/pollution/enterprise/{enterpriseId}/latest` - 获取最新污染数据

## 开发规范

### 代码风格

- 使用 Lombok 简化 Getter/Setter
- 使用 `@RequiredArgsConstructor` 进行构造函数注入
- Repository 使用 Spring Data JPA
- Service 层使用 `@Transactional` 注解

### 错误处理

- 使用标准 HTTP 状态码
- 返回 JSON 格式的错误信息
- 使用 Exception 进行错误处理

### 数据验证

- 使用 Jakarta Validation 注解
- 在 Entity 级别进行字段验证
- 在 Controller 层进行输入验证

## 后续开发任务

### 短期（Phase 2）
- [ ] 添加分页和排序功能
- [ ] 实现复杂查询过滤
- [ ] 添加单元测试
- [ ] 实现 API 文档（Swagger）

### 中期（Phase 3）
- [ ] 实现 WebSocket 实时数据推送
- [ ] 添加 Redis 缓存
- [ ] 实现用户认证（JWT）
- [ ] 实现权限控制（RBAC）

### 长期（Phase 4）
- [ ] 添加消息队列处理（RabbitMQ）
- [ ] 实现数据分析和报告
- [ ] 添加数据审计功能
- [ ] 性能优化和监控

## 常用命令

```bash
# 编译
mvn clean compile

# 运行
mvn spring-boot:run

# 测试
mvn test

# 构建 JAR
mvn clean package

# 跳过测试构建
mvn clean package -DskipTests

# 查看依赖树
mvn dependency:tree

# 运行构建的 JAR
java -jar target/mining-detection-system-1.0.0.jar
```

## 数据库初始化

所有初始化 SQL 已包含在以下文件中：
- `init-db.sql` - 数据库初始化脚本
- `src/main/resources/db/migration/V1__initial_schema.sql` - 迁移脚本

## 常见问题

### Q: 如何修改数据库连接？
A: 编辑 `src/main/resources/application.yml` 中的数据库配置部分。

### Q: 如何修改服务器端口？
A: 编辑 `src/main/resources/application.yml` 中的 `server.port` 配置。

### Q: 如何查看 SQL 执行日志？
A: 在 `application.yml` 中的 logging.level 中设置 `org.hibernate.SQL: DEBUG`。

## 文件清单

```
mining-backend/
├── pom.xml                          # Maven 配置
├── init-db.sql                      # 数据库初始化脚本
├── README.md                        # 项目说明文档
├── .gitignore                       # Git 忽略配置
└── src/
    ├── main/
    │   ├── java/com/mining/
    │   │   ├── MiningDetectionApplication.java
    │   │   ├── controller/          # 控制器层
    │   │   ├── service/             # 服务层
    │   │   ├── repository/          # 数据访问层
    │   │   └── entity/              # 实体类
    │   └── resources/
    │       ├── application.yml      # 应用配置
    │       └── db/migration/        # 数据库迁移脚本
    └── test/java/com/mining/        # 测试代码
```

## 修改历史

- **v1.0.0** (2024-01-01) - 初始版本，包含矿企、矿产、员工、污染数据的基础 CRUD 操作
