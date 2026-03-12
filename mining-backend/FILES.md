# Spring Boot 矿山检测系统 - 项目文件清单

## 核心配置文件

- `pom.xml` - Maven 项目配置
- `src/main/resources/application.yml` - Spring Boot 应用配置
- `Dockerfile` - Docker 镜像定义
- `docker-compose.yml` - Docker Compose 编排配置
- `.gitignore` - Git 忽略配置
- `.env.example` - 环境变量示例

## 应用入口

- `src/main/java/com/mining/MiningDetectionApplication.java` - Spring Boot 启动类

## 实体类 (Entity)

- `src/main/java/com/mining/entity/MineEnterprise.java` - 矿企实体
- `src/main/java/com/mining/entity/Mineral.java` - 矿产实体
- `src/main/java/com/mining/entity/Employee.java` - 员工实体
- `src/main/java/com/mining/entity/PollutionData.java` - 污染数据实体

## 数据访问层 (Repository)

- `src/main/java/com/mining/repository/MineEnterpriseRepository.java` - 矿企仓库
- `src/main/java/com/mining/repository/MineralRepository.java` - 矿产仓库
- `src/main/java/com/mining/repository/EmployeeRepository.java` - 员工仓库
- `src/main/java/com/mining/repository/PollutionDataRepository.java` - 污染数据仓库

## 业务逻辑层 (Service)

- `src/main/java/com/mining/service/MineEnterpriseService.java` - 矿企业务
- `src/main/java/com/mining/service/MineralService.java` - 矿产业务
- `src/main/java/com/mining/service/EmployeeService.java` - 员工业务
- `src/main/java/com/mining/service/PollutionDataService.java` - 污染数据业务

## API 控制器 (Controller)

- `src/main/java/com/mining/controller/MineEnterpriseController.java` - 矿企 API
- `src/main/java/com/mining/controller/MineralController.java` - 矿产 API
- `src/main/java/com/mining/controller/EmployeeController.java` - 员工 API
- `src/main/java/com/mining/controller/PollutionDataController.java` - 污染数据 API

## 数据库脚本

- `src/main/resources/db/migration/V1__initial_schema.sql` - Flyway 迁移脚本
- `init-db.sql` - 数据库初始化脚本（可独立运行）

## 文档

- `README.md` - 项目说明文档
- `INTEGRATION.md` - 前后端集成指南
- `DOCKER.md` - Docker 部署指南
- `PROJECT-GUIDE.md` - 完整项目指南
- `.github/copilot-instructions.md` - Copilot 开发指南

## 目录结构

```
mining-backend/
├── .github/
│   └── copilot-instructions.md
├── src/
│   ├── main/
│   │   ├── java/com/mining/
│   │   │   ├── MiningDetectionApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── MineEnterpriseController.java
│   │   │   │   ├── MineralController.java
│   │   │   │   ├── EmployeeController.java
│   │   │   │   └── PollutionDataController.java
│   │   │   ├── service/
│   │   │   │   ├── MineEnterpriseService.java
│   │   │   │   ├── MineralService.java
│   │   │   │   ├── EmployeeService.java
│   │   │   │   └── PollutionDataService.java
│   │   │   ├── repository/
│   │   │   │   ├── MineEnterpriseRepository.java
│   │   │   │   ├── MineralRepository.java
│   │   │   │   ├── EmployeeRepository.java
│   │   │   │   └── PollutionDataRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── MineEnterprise.java
│   │   │   │   ├── Mineral.java
│   │   │   │   ├── Employee.java
│   │   │   │   └── PollutionData.java
│   │   │   └── dto/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   │           └── V1__initial_schema.sql
│   └── test/java/com/mining/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── init-db.sql
├── .gitignore
├── .env.example
├── README.md
├── INTEGRATION.md
├── DOCKER.md
└── PROJECT-GUIDE.md
```

## 主要功能模块

### 1. 矿企管理 (MineEnterprise)
- 创建、读取、更新、删除矿企信息
- 按状态筛选企业
- 支持多种查询方式

### 2. 矿产管理 (Mineral)
- 管理矿产资源
- 追踪储量和品位
- 记录开采状态

### 3. 员工管理 (Employee)
- 维护员工档案
- 员工分配到企业
- 按部门和状态查询

### 4. 污染监测 (PollutionData)
- 记录实时污染数据
- 查询历史污染信息
- 按污染等级分类

## 依赖项

### 核心框架
- Spring Boot 3.2.0
- Spring Data JPA
- Hibernate

### 数据库
- MySQL 8.0
- MySQL Connector/J

### 工具库
- Lombok - 代码简化
- Jakarta Validation - 数据验证

### 构建工具
- Maven 3.8+
- Docker & Docker Compose

## 环境要求

- Java 17 或更高版本
- Maven 3.8 或更高版本
- MySQL 8.0 或更高版本
- Docker (可选，用于容器化部署)

## 开发工作流

1. **克隆或导入项目**
   - 使用 IDE 打开项目
   - IDE 会自动识别 Maven 项目

2. **配置数据库**
   - 修改 `application.yml` 的数据库连接信息
   - 运行 `init-db.sql` 初始化数据库

3. **编译项目**
   - `mvn clean compile`

4. **运行应用**
   - `mvn spring-boot:run`
   - 或构建后运行 JAR

5. **测试 API**
   - 使用 Postman、cURL 或集成的 API 文档

## 扩展点

### 添加新的业务模块

1. 在 `entity/` 创建新的实体类
2. 在 `repository/` 创建数据访问接口
3. 在 `service/` 实现业务逻辑
4. 在 `controller/` 创建 REST API 端点

### 常见扩展需求

- [ ] 添加分页查询功能
- [ ] 实现复杂的业务逻辑
- [ ] 集成缓存层（Redis）
- [ ] 实现用户认证（JWT）
- [ ] 添加 API 文档（Swagger）
- [ ] 性能监控和日志

## 常用命令

```bash
# 编译
mvn clean compile

# 测试
mvn test

# 构建 JAR
mvn clean package

# 运行
mvn spring-boot:run

# Docker 启动
docker-compose up -d

# 查看日志
docker-compose logs -f

# 停止 Docker
docker-compose down
```

## 联系方式

项目由开发团队维护。

## 更新日志

- **v1.0.0** (2024-01-01)
  - 初始版本
  - 实现矿企、矿产、员工、污染数据的基础 CRUD
  - 完整的 API 文档和集成指南
  - Docker 支持
