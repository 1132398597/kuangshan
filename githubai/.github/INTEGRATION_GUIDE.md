# 前后端集成指南

## 项目架构概述

前端已配置连接到后端API，前端在 `http://localhost:5173/`，后端在 `http://localhost:8080/`。

## 前端API模块

前端已经创建了以下API模块（位于 `src/api/`）：

### 1. config.js - API客户端配置
- 基于 Axios 的 HTTP 客户端
- 自动处理请求/响应拦截
- 基础URL：`http://localhost:8080/api`

### 2. mineEnterprise.js - 矿企管理API
```javascript
// 获取所有矿企
mineEnterpriseAPI.getAll()

// 按ID获取矿企
mineEnterpriseAPI.getById(id)

// 创建矿企
mineEnterpriseAPI.create(data)

// 更新矿企
mineEnterpriseAPI.update(id, data)

// 删除矿企
mineEnterpriseAPI.delete(id)
```

**端点**: `/api/mine-enterprises`

### 3. mineral.js - 矿产管理API
```javascript
// 获取所有矿产
mineralAPI.getAll()

// 按ID获取矿产
mineralAPI.getById(id)

// 根据矿企ID获取矿产
mineralAPI.getByMineId(mineId)

// 创建矿产
mineralAPI.create(data)

// 更新矿产
mineralAPI.update(id, data)

// 删除矿产
mineralAPI.delete(id)
```

**端点**: `/api/minerals`

### 4. employee.js - 员工管理API
```javascript
// 获取所有员工
employeeAPI.getAll()

// 按ID获取员工
employeeAPI.getById(id)

// 创建员工
employeeAPI.create(data)

// 更新员工
employeeAPI.update(id, data)

// 删除员工
employeeAPI.delete(id)
```

**端点**: `/api/employees`

### 5. pollution.js - 污染数据API
```javascript
// 获取所有污染数据
pollutionAPI.getAll()

// 按矿企ID获取污染数据
pollutionAPI.getByMineId(mineId)

// 按ID获取污染数据
pollutionAPI.getById(id)

// 创建污染数据
pollutionAPI.create(data)

// 更新污染数据
pollutionAPI.update(id, data)

// 删除污染数据
pollutionAPI.delete(id)
```

**端点**: `/api/pollution-data`

## 后端API要求

后端需要实现以下REST API端点：

### 矿企管理
| 方法 | 端点 | 功能 | 请求体 |
|------|------|------|--------|
| GET | `/api/mine-enterprises` | 获取所有矿企 | - |
| GET | `/api/mine-enterprises/{id}` | 按ID获取矿企 | - |
| POST | `/api/mine-enterprises` | 创建矿企 | `{name, location, contact, phone, status}` |
| PUT | `/api/mine-enterprises/{id}` | 更新矿企 | `{name, location, contact, phone, status}` |
| DELETE | `/api/mine-enterprises/{id}` | 删除矿企 | - |

### 矿产管理
| 方法 | 端点 | 功能 | 请求体 |
|------|------|------|--------|
| GET | `/api/minerals` | 获取所有矿产 | - |
| GET | `/api/minerals/{id}` | 按ID获取矿产 | - |
| POST | `/api/minerals` | 创建矿产 | `{name, type, company, reserves, grade, status}` |
| PUT | `/api/minerals/{id}` | 更新矿产 | `{name, type, company, reserves, grade, status}` |
| DELETE | `/api/minerals/{id}` | 删除矿产 | - |

### 员工管理
| 方法 | 端点 | 功能 | 请求体 |
|------|------|------|--------|
| GET | `/api/employees` | 获取所有员工 | - |
| GET | `/api/employees/{id}` | 按ID获取员工 | - |
| POST | `/api/employees` | 创建员工 | `{name, department, position, location, phone, joinDate, status}` |
| PUT | `/api/employees/{id}` | 更新员工 | `{name, department, position, location, phone, joinDate, status}` |
| DELETE | `/api/employees/{id}` | 删除员工 | - |

### 污染数据
| 方法 | 端点 | 功能 | 请求体 |
|------|------|------|--------|
| GET | `/api/pollution-data` | 获取所有污染数据 | - |
| GET | `/api/pollution-data/{id}` | 按ID获取污染数据 | - |
| GET | `/api/pollution-data/mine/{mineId}` | 按矿企ID获取污染数据 | - |
| POST | `/api/pollution-data` | 创建污染数据 | `{mineId, pollutionIndex, pm25, pm10, so2, no2}` |
| PUT | `/api/pollution-data/{id}` | 更新污染数据 | `{mineId, pollutionIndex, pm25, pm10, so2, no2}` |
| DELETE | `/api/pollution-data/{id}` | 删除污染数据 | - |

## 响应格式

后端应返回以下格式的JSON响应：

### 成功响应
```json
{
  "code": 0,
  "message": "success",
  "data": {
    // 实际数据
  }
}
```

或直接返回数据数组/对象：
```json
{
  "data": [...],
  "total": 100,
  "page": 1,
  "pageSize": 10
}
```

或简单的数据数组：
```json
[...]
```

### 错误响应
```json
{
  "code": -1,
  "message": "error message",
  "data": null
}
```

## 跨域配置（CORS）

后端需要配置CORS允许来自前端的请求：

### Spring Boot配置示例
```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
            }
        };
    }
}
```

## 测试前后端交互

### 1. 确保后端运行在 http://localhost:8080
### 2. 确保前端运行在 http://localhost:5173
### 3. 打开浏览器开发工具（F12）查看Network标签
### 4. 执行操作（添加、编辑、删除）观察API调用

## 常见问题

### 1. CORS错误
**症状**: 浏览器控制台出现 `Access to XMLHttpRequest blocked by CORS policy`

**解决**: 确保后端配置了CORS，允许来自 `http://localhost:5173` 的请求。

### 2. API返回404
**症状**: 网络标签显示404错误

**解决**: 检查：
- 后端是否运行在 `http://localhost:8080`
- API端点是否与预期匹配
- 请求方法（GET/POST/PUT/DELETE）是否正确

### 3. API超时
**症状**: 页面卡住，网络请求超过10秒后失败

**解决**: 
- 检查后端是否响应缓慢
- 增加axios超时时间（在 `src/api/config.js` 中修改）

### 4. 前端数据不更新
**症状**: 虽然API调用成功，但页面数据没有变化

**解决**: 
- 检查响应数据结构是否与前端期望匹配
- 在 `fetchData` 方法中正确访问 `response.data` 或 `response`

## 开发建议

1. **数据结构一致性**：确保API返回的数据字段与前端期望的字段名称一致

2. **ID字段**：所有实体都应该有 `id` 字段作为主键

3. **状态字段**：
   - 矿企：`active`（正常）、`inactive`（停运）、`maintenance`（维护）
   - 矿产：`mining`（开采中）、`exploration`（勘探中）、`closed`（已关闭）
   - 员工：`working`（在职）、`leave`（休假）、`resigned`（离职）

4. **地理坐标**：矿企应包含 `coordinates` 字段，格式为 `"longitude,latitude"`

5. **错误处理**：前端已配置基本的错误处理，后端异常会显示在浏览器alert中

## 后续优化

- [ ] 添加加载状态指示器
- [ ] 实现页码分页功能
- [ ] 添加搜索和过滤功能
- [ ] 实现数据导出（Excel）
- [ ] WebSocket实时数据推送
- [ ] 用户认证和授权
