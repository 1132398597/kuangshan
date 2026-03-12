# 前后端集成指南

## 项目结构

本项目包含两个独立的工作目录：

1. **前端项目** (`/githubai`) - Vue 3 + Vite
2. **后端项目** (`/mining-backend`) - Spring Boot 3 + MySQL

## 后端部署步骤

### 1. 安装 Java 环境

- 下载并安装 [JDK 17+](https://jdk.java.net/17/)
- 设置 `JAVA_HOME` 环境变量

### 2. 安装 Maven

- 下载 [Maven 3.8+](https://maven.apache.org/download.cgi)
- 设置 `MAVEN_HOME` 环境变量
- 在 PATH 中添加 `%MAVEN_HOME%\bin`

### 3. 安装并启动 MySQL

```bash
# Windows - 使用 MySQL Installer 或 Docker
docker run --name mysql8 -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:8.0

# 或使用本地 MySQL 服务
# 确保 MySQL 正在运行
```

#### 在 MySQL 中初始化数据库

方式一：使用 SQL 脚本
```bash
mysql -u root -p < init-db.sql
```

方式二：在 MySQL 客户端手动执行
```bash
# 连接到 MySQL
mysql -u root -p

# 在 MySQL 提示符下执行 init-db.sql 中的 SQL 语句
```

### 4. 编译并运行后端

```bash
# 进入后端目录
cd mining-backend

# 编译项目
mvn clean compile

# 或直接运行（会自动编译）
mvn spring-boot:run

# 或构建 JAR 后运行
mvn clean package
java -jar target/mining-detection-system-1.0.0.jar
```

后端服务启动后访问 `http://localhost:8080`

### 5. 验证后端 API

```bash
# 测试矿企 API
curl http://localhost:8080/api/v1/enterprises

# 应该返回示例数据
```

## 前端配置

### 1. 配置 API 基础 URL

在前端项目中，需要配置后端 API 地址。创建 `src/api/client.js`：

```javascript
import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api/v1'

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
apiClient.interceptors.request.use(
  config => config,
  error => Promise.reject(error)
)

// 响应拦截器
apiClient.interceptors.response.use(
  response => response.data,
  error => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

export default apiClient
```

### 2. 创建 API 服务模块

`src/api/services.js`:

```javascript
import apiClient from './client'

// 矿企 API
export const enterpriseAPI = {
  getAll: () => apiClient.get('/enterprises'),
  getById: (id) => apiClient.get(`/enterprises/${id}`),
  create: (data) => apiClient.post('/enterprises', data),
  update: (id, data) => apiClient.put(`/enterprises/${id}`, data),
  delete: (id) => apiClient.delete(`/enterprises/${id}`),
  getByStatus: (status) => apiClient.get(`/enterprises/status/${status}`)
}

// 矿产 API
export const mineralAPI = {
  getAll: () => apiClient.get('/minerals'),
  getById: (id) => apiClient.get(`/minerals/${id}`),
  getByEnterprise: (enterpriseId) => apiClient.get(`/minerals/enterprise/${enterpriseId}`),
  create: (data) => apiClient.post('/minerals', data),
  update: (id, data) => apiClient.put(`/minerals/${id}`, data),
  delete: (id) => apiClient.delete(`/minerals/${id}`)
}

// 员工 API
export const employeeAPI = {
  getAll: () => apiClient.get('/employees'),
  getById: (id) => apiClient.get(`/employees/${id}`),
  getByEnterprise: (enterpriseId) => apiClient.get(`/employees/enterprise/${enterpriseId}`),
  getByDepartment: (department) => apiClient.get(`/employees/department/${department}`),
  create: (data) => apiClient.post('/employees', data),
  update: (id, data) => apiClient.put(`/employees/${id}`, data),
  delete: (id) => apiClient.delete(`/employees/${id}`),
  assignToEnterprise: (employeeId, enterpriseId) => 
    apiClient.post(`/employees/${employeeId}/assign/${enterpriseId}`)
}

// 污染数据 API
export const pollutionAPI = {
  getAll: () => apiClient.get('/pollution'),
  getByEnterprise: (enterpriseId) => apiClient.get(`/pollution/enterprise/${enterpriseId}`),
  getLatest: (enterpriseId) => apiClient.get(`/pollution/enterprise/${enterpriseId}/latest`),
  getByRange: (enterpriseId, startTime, endTime) => 
    apiClient.get(`/pollution/enterprise/${enterpriseId}/range`, {
      params: { startTime, endTime }
    }),
  record: (data) => apiClient.post('/pollution', data),
  delete: (id) => apiClient.delete(`/pollution/${id}`)
}
```

### 3. 在 Vue 组件中使用 API

更新 `src/views/Home.vue`:

```javascript
import { enterpriseAPI, pollutionAPI } from '../api/services'

export default {
  // ... 其他配置
  
  async mounted() {
    await this.loadEnterprises()
  },
  
  methods: {
    async loadEnterprises() {
      try {
        this.enterprises = await enterpriseAPI.getAll()
      } catch (error) {
        console.error('加载企业失败:', error)
      }
    },
    
    async selectMine(mineId) {
      try {
        const latestPollution = await pollutionAPI.getLatest(mineId)
        this.selectedMine = latestPollution
      } catch (error) {
        console.error('加载污染数据失败:', error)
      }
    }
  }
}
```

## 跨域配置

### 后端已配置 CORS

在所有 Controller 上已添加 `@CrossOrigin(origins = "*")`，允许来自任何源的请求。

### 环境特定配置

如需更严格的 CORS 配置，可以创建 `src/main/java/com/mining/config/CorsConfig.java`:

```java
package com.mining.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:5173", "http://localhost:3000")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
```

## 数据格式规范

### 矿企对象

```json
{
  "id": 1,
  "enterpriseCode": "ME001",
  "enterpriseName": "中国神华矿业有限公司",
  "location": "陕西省榆林市",
  "contactPerson": "李明",
  "contactPhone": "029-8765-4321",
  "status": "ACTIVE",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### 矿产对象

```json
{
  "id": 1,
  "mineralCode": "MN001",
  "mineralName": "神华煤矿",
  "mineralType": "煤炭",
  "enterprise": { "id": 1, "enterpriseName": "..." },
  "reserves": 5000.00,
  "grade": 72.50,
  "status": "MINING",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### 员工对象

```json
{
  "id": 1,
  "employeeCode": "EMP001",
  "employeeName": "张三",
  "department": "安全管理",
  "position": "安全经理",
  "location": "陕西省榆林市",
  "phone": "13800138000",
  "joinDate": "2020-01-15",
  "enterprise": { "id": 1, "enterpriseName": "..." },
  "status": "WORKING",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### 污染数据对象

```json
{
  "id": 1,
  "enterprise": { "id": 1, "enterpriseName": "..." },
  "pollutionIndex": 85.00,
  "pm25": 68.00,
  "pm10": 102.00,
  "so2": 51.00,
  "no2": 59.50,
  "level": "HIGH",
  "recordTime": "2024-01-01T10:00:00",
  "createdAt": "2024-01-01T10:00:00"
}
```

## 错误处理

后端返回的错误响应格式（可选实现）：

```json
{
  "status": 400,
  "message": "请求参数错误",
  "timestamp": "2024-01-01T10:00:00",
  "path": "/api/v1/enterprises"
}
```

前端应该统一处理这些错误：

```javascript
apiClient.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response) {
      const { status, data } = error.response
      message.error(data.message || `错误代码: ${status}`)
    } else if (error.request) {
      message.error('无响应，请检查网络连接')
    } else {
      message.error('请求失败: ' + error.message)
    }
    return Promise.reject(error)
  }
)
```

## 环境配置

### 开发环境

```bash
# 后端
java -jar target/mining-detection-system-1.0.0.jar \
  --spring.datasource.url=jdbc:mysql://localhost:3306/mining_db \
  --spring.datasource.username=root \
  --spring.datasource.password=root

# 前端
npm run dev
```

### 生产环境

```bash
# 后端构建和部署
mvn clean package -DskipTests
java -jar target/mining-detection-system-1.0.0.jar \
  --spring.datasource.url=jdbc:mysql://prod-db:3306/mining_db \
  --spring.datasource.username=prod_user \
  --spring.datasource.password=prod_password

# 前端构建
npm run build
# 将 dist 文件夹部署到 Web 服务器（Nginx、Apache 等）
```

## 常见问题

### Q: 前端无法连接后端？
A: 
1. 检查后端是否正在运行 (`http://localhost:8080`)
2. 检查防火墙是否允许 8080 端口
3. 确保 CORS 配置正确

### Q: 如何从其他机器连接后端？
A: 修改 `application.yml` 中的数据库 URL，将 `localhost` 改为服务器 IP 地址

### Q: 如何处理跨域问题？
A: 后端已配置 CORS，但如果仍有问题，可以：
1. 使用代理（在 `vite.config.js` 中配置）
2. 在后端配置更详细的 CORS 规则

## 下一步

1. 完成前端与后端的集成测试
2. 实现更复杂的业务逻辑
3. 添加用户认证和授权
4. 性能优化和监控
5. 部署到生产环境
