# 矿山检测系统 - 完整项目指南

## 项目概述

矿山检测系统是一个现代化的 Web 应用程序，用于矿山企业的管理和污染监测。该系统包含一个 Vue 3 前端和一个 Spring Boot 后端。

### 核心功能

1. **矿企管理** - 管理矿山企业基本信息
2. **矿产管理** - 追踪矿产资源和开采状态
3. **员工管理** - 维护员工档案和部门组织
4. **污染监测** - 实时记录和显示污染数据

## 系统架构

```
┌─────────────────────────────────────────────────────────┐
│                      用户浏览器                          │
│                  (Vue 3 + Vite)                         │
│            Running on http://localhost:5173            │
└──────────────────────┬──────────────────────────────────┘
                       │ HTTP/REST API
                       │
┌──────────────────────▼──────────────────────────────────┐
│                  Spring Boot 应用                       │
│              Running on http://localhost:8080          │
│                                                         │
│  ┌──────────────────────────────────────────────────┐  │
│  │           REST API Controllers                   │  │
│  │  (/api/v1/enterprises, minerals, employees...)  │  │
│  └──────────────────────────────────────────────────┘  │
│                       │                                 │
│  ┌──────────────────────────────────────────────────┐  │
│  │    Service Layer (Business Logic)                │  │
│  ├──────────────────────────────────────────────────┤  │
│  │ - MineEnterpriseService                          │  │
│  │ - MineralService                                 │  │
│  │ - EmployeeService                                │  │
│  │ - PollutionDataService                           │  │
│  └──────────────────────────────────────────────────┘  │
│                       │                                 │
│  ┌──────────────────────────────────────────────────┐  │
│  │    Repository Layer (Data Access)                │  │
│  │         Spring Data JPA + Hibernate              │  │
│  └──────────────────────────────────────────────────┘  │
└──────────────────────┬──────────────────────────────────┘
                       │ JDBC
┌──────────────────────▼──────────────────────────────────┐
│                   MySQL 数据库                          │
│                (localhost:3306)                         │
│                                                         │
│  ├── mine_enterprise (矿企表)                           │
│  ├── mineral (矿产表)                                   │
│  ├── employee (员工表)                                  │
│  └── pollution_data (污染数据表)                        │
└─────────────────────────────────────────────────────────┘
```

## 目录结构

### 前端项目 (/githubai)

```
githubai/
├── src/
│   ├── views/                    # 页面组件
│   │   ├── Home.vue             # 首页（地图）
│   │   ├── MineEnterprise.vue   # 矿企管理
│   │   ├── MineralManagement.vue # 矿产管理
│   │   └── EmployeeManagement.vue # 员工管理
│   ├── components/               # 可复用组件
│   │   ├── Sidebar.vue          # 导航栏
│   │   ├── ChinaMap.vue         # 地图组件
│   │   └── PollutionPanel.vue   # 污染信息面板
│   ├── router/                   # 路由配置
│   │   └── index.js
│   ├── App.vue                   # 根组件
│   ├── main.js                   # 入口文件
│   └── style.css                 # 全局样式
├── index.html
├── vite.config.js
├── package.json
└── README.md
```

### 后端项目 (/mining-backend)

```
mining-backend/
├── src/
│   ├── main/
│   │   ├── java/com/mining/
│   │   │   ├── MiningDetectionApplication.java   # 启动类
│   │   │   ├── entity/                           # 数据实体
│   │   │   │   ├── MineEnterprise.java
│   │   │   │   ├── Mineral.java
│   │   │   │   ├── Employee.java
│   │   │   │   └── PollutionData.java
│   │   │   ├── repository/                       # 数据仓库
│   │   │   ├── service/                          # 业务服务
│   │   │   ├── controller/                       # API 控制器
│   │   │   └── dto/                              # 数据传输对象
│   │   └── resources/
│   │       ├── application.yml                   # 应用配置
│   │       └── db/migration/                     # 数据库脚本
│   └── test/java/com/mining/                     # 测试代码
├── pom.xml                                       # Maven 配置
├── Dockerfile                                    # Docker 镜像
├── docker-compose.yml                            # Docker Compose
├── init-db.sql                                   # 数据库初始化
├── README.md                                     # 项目说明
├── INTEGRATION.md                                # 集成指南
└── DOCKER.md                                     # Docker 指南
```

## 快速开始

### 1. 环境准备

#### 前置条件检查

```bash
# 检查 Java
java -version

# 检查 Maven（可选，但推荐）
mvn -version

# 检查 Node.js
node -version
npm -version

# 检查 MySQL
mysql -version
```

### 2. 启动后端服务

#### 选项 A：使用 Docker Compose（推荐）

```bash
cd mining-backend
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f app
```

#### 选项 B：本地启动

```bash
# 1. 确保 MySQL 正在运行
# MySQL 连接: localhost:3306, 用户: root, 密码: root

# 2. 初始化数据库
mysql -u root -p < mining-backend/init-db.sql

# 3. 编译并运行后端
cd mining-backend
mvn spring-boot:run

# 或构建后运行
mvn clean package
java -jar target/mining-detection-system-1.0.0.jar
```

后端服务启动后：
- API 地址: http://localhost:8080
- 测试: curl http://localhost:8080/api/v1/enterprises

### 3. 启动前端服务

```bash
cd githubai

# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 访问应用
# 浏览器打开 http://localhost:5173
```

## 使用教程

### 首页功能

1. **查看地图**
   - 页面加载时自动显示中国地图
   - 地图上显示所有矿山位置和污染等级

2. **选择矿山**
   - 点击地图上的矿山标记
   - 右侧污染信息面板更新显示该矿山的数据

3. **查看污染数据**
   - 污染指数：综合污染评分
   - 污染等级：低/中/高
   - 详细指标：PM2.5, PM10, SO2, NO2

### 矿企管理

1. **查看企业列表**
   - 显示所有注册的矿山企业
   - 显示企业状态（正常/停运/维护）

2. **添加企业**
   - 点击"+ 添加矿企"按钮
   - 填写企业信息
   - 点击保存

3. **编辑和删除**
   - 点击表格中的编辑按钮修改信息
   - 点击删除按钮移除企业

### 矿产管理

1. **查看矿产清单**
   - 显示所有矿产及其详细信息
   - 显示开采状态

2. **管理矿产**
   - 添加、编辑、删除矿产
   - 更新储量和品位信息

### 员工管理

1. **员工档案**
   - 维护所有员工的基本信息
   - 追踪员工部门和职位

2. **员工状态**
   - 标记员工在职/休假/离职
   - 记录入职日期

## API 快速参考

### 常用端点

```bash
# 获取所有矿企
GET /api/v1/enterprises

# 创建矿企
POST /api/v1/enterprises
Content-Type: application/json
{
  "enterpriseCode": "ME005",
  "enterpriseName": "新矿企",
  "location": "位置",
  "contactPerson": "联系人",
  "contactPhone": "电话",
  "status": "ACTIVE"
}

# 获取矿企详情
GET /api/v1/enterprises/{id}

# 更新矿企
PUT /api/v1/enterprises/{id}

# 删除矿企
DELETE /api/v1/enterprises/{id}

# 获取污染数据
GET /api/v1/pollution/enterprise/{enterpriseId}

# 记录污染数据
POST /api/v1/pollution
{
  "enterprise": {"id": 1},
  "pollutionIndex": 85.0,
  "pm25": 68.0,
  "pm10": 102.0,
  "so2": 51.0,
  "no2": 59.5,
  "level": "HIGH",
  "recordTime": "2024-01-01T10:00:00"
}
```

详见：[INTEGRATION.md](INTEGRATION.md)

## 配置参考

### 前端配置 (vite.config.js)

```javascript
// 修改 API 代理
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

### 后端配置 (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mining_db
    username: root
    password: root
```

## 常见任务

### 修改数据库连接

**后端：** 编辑 `src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://新地址:3306/mining_db
    username: 新用户名
    password: 新密码
```

### 修改 API 端口

**后端：** 编辑 `src/main/resources/application.yml`

```yaml
server:
  port: 新端口号
```

**前端：** 编辑 `src/api/client.js`

```javascript
const API_BASE_URL = 'http://localhost:新端口号/api/v1'
```

### 构建生产版本

**前端：**
```bash
cd githubai
npm run build
# 输出在 dist/ 目录
```

**后端：**
```bash
cd mining-backend
mvn clean package -DskipTests
# 输出 target/mining-detection-system-1.0.0.jar
```

## 故障排除

### 常见问题

| 问题 | 解决方案 |
|------|--------|
| 前端无法连接后端 | 检查后端是否运行，CORS 是否配置，防火墙设置 |
| 数据库连接失败 | 检查 MySQL 是否运行，连接参数是否正确 |
| 端口被占用 | 修改配置文件中的端口号 |
| Maven 命令未找到 | 安装 Maven 或添加到 PATH 环境变量 |
| npm 命令未找到 | 安装 Node.js 或添加到 PATH 环境变量 |

### 调试技巧

```bash
# 查看后端日志
docker-compose logs -f app

# 查看数据库
mysql -u root -p mining_db
SELECT * FROM mine_enterprise;

# 测试 API
curl -X GET http://localhost:8080/api/v1/enterprises

# 检查端口
# Windows: netstat -ano | findstr :8080
# Linux/Mac: lsof -i :8080
```

## 软件架构设计原则

### 前端

- **组件化** - 可复用的 Vue 组件
- **路由管理** - Vue Router 管理页面导航
- **API 层** - 统一的 API 客户端

### 后端

- **分层架构** - Controller → Service → Repository → Database
- **RESTful API** - 标准的 HTTP 方法
- **数据验证** - 在 Entity 层进行验证
- **事务管理** - `@Transactional` 注解

## 性能优化建议

### 前端

1. 启用代码分割和懒加载
2. 压缩资源文件
3. 使用 CDN 加速

### 后端

1. 添加数据库索引（已在初始化脚本中完成）
2. 实现分页查询
3. 添加缓存层（Redis）
4. 使用连接池优化数据库连接

## 安全性建议

- [ ] 实现用户认证（JWT）
- [ ] 添加 API 速率限制
- [ ] 实现权限控制
- [ ] 加密敏感数据
- [ ] 定期安全审计

## 部署建议

### 开发环境
- 本地 Maven + Node.js
- 本地 MySQL

### 测试环境
- Docker + Docker Compose
- 独立的测试数据库

### 生产环境
- Kubernetes 集群
- 云数据库 (AWS RDS, Azure Database 等)
- CDN 加速静态资源
- 负载均衡

## 技术栈总结

| 组件 | 版本 | 用途 |
|------|------|------|
| Vue | 3.x | 前端框架 |
| Vite | 4.x | 前端构建工具 |
| Vue Router | 4.x | 前端路由 |
| ECharts | 5.x | 数据可视化 |
| Axios | 1.x | HTTP 客户端 |
| Spring Boot | 3.2 | 后端框架 |
| Spring Data JPA | Latest | ORM 框架 |
| Hibernate | Latest | ORM 实现 |
| MySQL | 8.0+ | 关系数据库 |
| Lombok | Latest | Java 代码简化 |
| Docker | Latest | 容器化部署 |

## 联系和支持

如有问题或建议，请联系开发团队。

## 许可证

MIT License

---

**最后更新**: 2024 年 1 月
**项目版本**: 1.0.0
