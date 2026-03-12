# 矿山检测系统 - Spring Boot 后端

一个基于 Spring Boot 3 和 MySQL 的矿山企业管理和污染监测系统后端服务。

## 技术栈

- **Spring Boot**: 3.2.0
- **Java**: 17
- **数据库**: MySQL 8.0+
- **ORM**: Spring Data JPA + Hibernate
- **构建工具**: Maven
- **数据验证**: Jakarta Validation
- **增强**: Lombok

## 项目结构

```
src/
├── main/
│   ├── java/com/mining/
│   │   ├── MiningDetectionApplication.java  # 主应用入口
│   │   ├── controller/                      # REST API 控制器
│   │   ├── service/                         # 业务逻辑层
│   │   ├── repository/                      # 数据访问层
│   │   ├── entity/                          # JPA 实体类
│   │   └── dto/                             # 数据传输对象（预留）
│   ├── resources/
│   │   ├── application.yml                  # 应用配置
│   │   └── db/migration/                    # 数据库迁移脚本
│   └── sql/
│       └── init-db.sql                      # 数据库初始化脚本
└── test/
    └── java/com/mining/                     # 单元测试
```

## 快速开始

### 前置条件

1. **Java 17+**
2. **MySQL 8.0+**
3. **Maven 3.8+**

### 1. 配置数据库

#### 方式一：使用提供的 SQL 脚本

```bash
# 在 MySQL 命令行中运行
source init-db.sql
```

或者：

```bash
mysql -u root -p < init-db.sql
```

#### 方式二：手动执行

```sql
CREATE DATABASE mining_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mining_db;

-- 然后执行 init-db.sql 中的创建表和插入数据的语句
```

### 2. 修改数据库配置

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mining_db?useSSL=false&serverTimezone=UTC
    username: root           # 修改为你的 MySQL 用户名
    password: root           # 修改为你的 MySQL 密码
```

### 3. 编译项目

```bash
mvn clean compile
```

### 4. 运行应用

```bash
mvn spring-boot:run
```

或者构建后运行：

```bash
mvn clean package
java -jar target/mining-detection-system-1.0.0.jar
```

应用启动后访问 `http://localhost:8080`

## API 文档

### 矿企管理

| 方法 | 端点 | 描述 |
|------|------|------|
| GET | `/api/v1/enterprises` | 获取所有矿企 |
| GET | `/api/v1/enterprises/{id}` | 按 ID 获取矿企 |
| GET | `/api/v1/enterprises/code/{code}` | 按编码获取矿企 |
| GET | `/api/v1/enterprises/status/{status}` | 按状态获取矿企 |
| POST | `/api/v1/enterprises` | 创建矿企 |
| PUT | `/api/v1/enterprises/{id}` | 更新矿企 |
| DELETE | `/api/v1/enterprises/{id}` | 删除矿企 |

### 矿产管理

| 方法 | 端点 | 描述 |
|------|------|------|
| GET | `/api/v1/minerals` | 获取所有矿产 |
| GET | `/api/v1/minerals/{id}` | 按 ID 获取矿产 |
| GET | `/api/v1/minerals/code/{code}` | 按编码获取矿产 |
| GET | `/api/v1/minerals/enterprise/{enterpriseId}` | 按企业获取矿产 |
| GET | `/api/v1/minerals/status/{status}` | 按状态获取矿产 |
| POST | `/api/v1/minerals` | 创建矿产 |
| PUT | `/api/v1/minerals/{id}` | 更新矿产 |
| DELETE | `/api/v1/minerals/{id}` | 删除矿产 |

### 员工管理

| 方法 | 端点 | 描述 |
|------|------|------|
| GET | `/api/v1/employees` | 获取所有员工 |
| GET | `/api/v1/employees/{id}` | 按 ID 获取员工 |
| GET | `/api/v1/employees/code/{code}` | 按编码获取员工 |
| GET | `/api/v1/employees/enterprise/{enterpriseId}` | 按企业获取员工 |
| GET | `/api/v1/employees/status/{status}` | 按状态获取员工 |
| GET | `/api/v1/employees/department/{department}` | 按部门获取员工 |
| POST | `/api/v1/employees` | 创建员工 |
| PUT | `/api/v1/employees/{id}` | 更新员工 |
| DELETE | `/api/v1/employees/{id}` | 删除员工 |
| POST | `/api/v1/employees/{employeeId}/assign/{enterpriseId}` | 分配员工到企业 |

### 污染数据

| 方法 | 端点 | 描述 |
|------|------|------|
| GET | `/api/v1/pollution` | 获取所有污染数据 |
| GET | `/api/v1/pollution/enterprise/{enterpriseId}` | 按企业获取污染数据 |
| GET | `/api/v1/pollution/enterprise/{enterpriseId}/latest` | 获取最新污染数据 |
| GET | `/api/v1/pollution/enterprise/{enterpriseId}/range` | 获取时间范围内的数据 |
| GET | `/api/v1/pollution/level/{level}` | 按污染等级获取数据 |
| POST | `/api/v1/pollution` | 记录污染数据 |
| DELETE | `/api/v1/pollution/{id}` | 删除污染数据 |

## 请求示例

### 创建矿企

```bash
curl -X POST http://localhost:8080/api/v1/enterprises \
  -H "Content-Type: application/json" \
  -d '{
    "enterpriseCode": "ME005",
    "enterpriseName": "新矿业有限公司",
    "location": "位置",
    "contactPerson": "联系人",
    "contactPhone": "电话号码",
    "status": "ACTIVE"
  }'
```

### 创建员工

```bash
curl -X POST http://localhost:8080/api/v1/employees \
  -H "Content-Type: application/json" \
  -d '{
    "employeeCode": "EMP006",
    "employeeName": "员工名称",
    "department": "部门",
    "position": "职位",
    "location": "位置",
    "phone": "电话",
    "joinDate": "2024-01-01",
    "status": "WORKING"
  }'
```

### 记录污染数据

```bash
curl -X POST http://localhost:8080/api/v1/pollution \
  -H "Content-Type: application/json" \
  -d '{
    "enterprise": {"id": 1},
    "pollutionIndex": 80.5,
    "pm25": 65.0,
    "pm10": 98.0,
    "so2": 49.0,
    "no2": 56.0,
    "level": "HIGH",
    "recordTime": "2024-01-01T10:00:00"
  }'
```

## 数据库设计

### 主要表

#### mine_enterprise (矿企表)
- 存储矿业企业信息
- 状态: ACTIVE(正常), INACTIVE(停运), MAINTENANCE(维护)

#### mineral (矿产表)
- 存储矿产信息
- 状态: MINING(开采中), EXPLORATION(勘探中), CLOSED(已关闭)
- 与 mine_enterprise 有多对一关系

#### employee (员工表)
- 存储员工信息
- 状态: WORKING(在职), LEAVE(休假), RESIGNED(离职)
- 与 mine_enterprise 有多对一关系

#### pollution_data (污染数据表)
- 存储实时污染监测数据
- 等级: LOW(低), MEDIUM(中), HIGH(高)
- 与 mine_enterprise 有多对一关系

## 环境变量

可以通过命令行启动时设置：

```bash
java -jar target/mining-detection-system-1.0.0.jar \
  --spring.datasource.url=jdbc:mysql://localhost:3306/mining_db \
  --spring.datasource.username=root \
  --spring.datasource.password=password
```

## 日志配置

日志级别配置在 `application.yml` 中：

```yaml
logging:
  level:
    root: INFO
    com.mining: DEBUG
```

## 性能优化建议

1. **数据库索引** - 已在初始化脚本中创建关键索引
2. **JPA 配置** - 使用 open-in-view: false 来避免 LazyInitializationException
3. **分页查询** - 对大数据量使用分页（待添加）
4. **缓存** - 考虑添加 Redis 缓存（待添加）

## 扩展建议

### Phase 2
- [ ] 添加分页和排序功能
- [ ] 实现复杂查询和过滤
- [ ] 添加单元测试和集成测试
- [ ] 实现数据审计（谁改了什么时间）

### Phase 3
- [ ] WebSocket 实时数据推送
- [ ] Redis 缓存集成
- [ ] 消息队列（如 RabbitMQ）
- [ ] 用户认证和授权（JWT）
- [ ] API 文档生成（Swagger/OpenAPI）

## 故障排除

### 连接数据库失败

```bash
# 检查 MySQL 是否运行
mysql -u root -p -e "SELECT 1;"

# 检查数据库是否存在
mysql -u root -p -e "SHOW DATABASES;"
```

### Port 8080 已被占用

修改 `application.yml`：

```yaml
server:
  port: 8081  # 改为其他端口
```

## 许可证

MIT

## 联系方式

如有问题，请联系开发团队。
