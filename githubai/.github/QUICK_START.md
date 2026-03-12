# 前后端联动 - 快速开始

## 🚀 快速启动

### 1. 启动前端
```bash
cd /github/ai
npm run dev
```
前端运行在：http://localhost:5173

### 2. 启动后端（Spring Boot）
```bash
cd /path/to/backend
# 使用Maven
mvn spring-boot:run

# 或使用Gradle
gradle bootRun

# 或直接运行jar包
java -jar app.jar
```
后端运行在：http://localhost:8080

## 📝 API调用流程

### 前端调用后端的通用流程：

1. **导入API模块**
```javascript
import mineEnterpriseAPI from '../api/mineEnterprise'
```

2. **在组件中使用**
```javascript
async fetchCompanies() {
  try {
    const response = await mineEnterpriseAPI.getAll()
    this.companies = response.data || response || []
  } catch (error) {
    console.error('Failed to fetch:', error)
  }
}
```

3. **在mounted钩子中调用**
```javascript
mounted() {
  this.fetchCompanies()
}
```

## 🔌 已改造的前端组件

### 1. MineEnterprise.vue（矿企管理）
- ✅ 自动加载矿企列表
- ✅ 新增矿企
- ✅ 编辑矿企
- ✅ 删除矿企

### 2. MineralManagement.vue（矿产管理）
- ✅ 自动加载矿产列表
- ✅ 新增矿产
- ✅ 编辑矿产
- ✅ 删除矿产

### 3. EmployeeManagement.vue（员工管理）
- ✅ 自动加载员工列表
- ✅ 新增员工
- ✅ 编辑员工
- ✅ 删除员工

### 4. ChinaMap.vue（地图）
- ✅ 自动加载矿企列表
- ✅ 自动加载污染数据
- ✅ 在地图上显示矿企和污染指数
- ✅ 点击矿企查看详情

### 5. PollutionPanel.vue（污染信息面板）
- ✅ 显示所选矿企的污染信息
- ✅ 自动获取污染详情

## 🧪 测试API连接

### 方法1：浏览器开发工具
1. 打开浏览器 → F12 → Network标签
2. 在前端执行操作（如点击"矿企管理"）
3. 观察network请求：
   - 应该看到 `mine-enterprises` 的请求
   - 如果成功：状态码200，响应体包含矿企数据
   - 如果失败：状态码非200，查看错误信息

### 方法2：在浏览器控制台测试
```javascript
// 在Console中运行
import mineEnterpriseAPI from './api/mineEnterprise'

// 测试API
mineEnterpriseAPI.getAll()
  .then(data => console.log('Success:', data))
  .catch(err => console.error('Error:', err))
```

### 方法3：使用Postman或curl
```bash
# 测试获取矿企列表
curl http://localhost:8080/api/mine-enterprises

# 测试创建矿企
curl -X POST http://localhost:8080/api/mine-enterprises \
  -H "Content-Type: application/json" \
  -d '{"name":"新矿企","location":"陕西","contact":"联系人","phone":"13800000000","status":"active"}'
```

## 🔍 调试技巧

### 1. 检查API配置
打开 `src/api/config.js`，确认：
```javascript
const API_BASE_URL = 'http://localhost:8080/api'
```

### 2. 查看实际请求
在浏览器开发工具Network标签中：
- 点击请求查看Request Headers和Response
- 检查状态码和返回数据

### 3. 启用详细日志
在 `src/api/config.js` 中添加日志：
```javascript
apiClient.interceptors.request.use(config => {
  console.log('Request:', config.method, config.url, config.data)
  return config
})

apiClient.interceptors.response.use(response => {
  console.log('Response:', response)
  return response.data
})
```

## 📋 数据格式示例

### 矿企（MineEnterprise）
```json
{
  "id": "ME001",
  "name": "中国神华矿业有限公司",
  "location": "陕西省榆林市",
  "contact": "李明",
  "phone": "029-8765-4321",
  "status": "active"
}
```

### 矿产（Mineral）
```json
{
  "id": "MN001",
  "name": "神华煤矿",
  "type": "煤炭",
  "company": "中国神华矿业有限公司",
  "reserves": 5000,
  "grade": 72.5,
  "status": "mining"
}
```

### 员工（Employee）
```json
{
  "id": "EMP001",
  "name": "张三",
  "department": "安全管理",
  "position": "安全经理",
  "location": "陕西省榆林市",
  "phone": "13800138000",
  "joinDate": "2020-01-15",
  "status": "working"
}
```

### 污染数据（Pollution）
```json
{
  "id": "P001",
  "mineId": "ME001",
  "pollutionIndex": 85,
  "pm25": 68,
  "pm10": 102,
  "so2": 51,
  "no2": 60,
  "timestamp": "2024-03-10T10:30:00"
}
```

## ❌ 常见错误及解决方案

| 错误信息 | 原因 | 解决方案 |
|---------|------|--------|
| CORS error | 后端未配置CORS | 在Spring Boot中配置CORS |
| 404 Not Found | API端点不存在 | 检查后端路由映射 |
| 500 Server Error | 后端处理异常 | 查看后端日志 |
| Connection refused | 后端未运行 | 启动Spring Boot应用 |
| timeout | 后端响应缓慢 | 检查数据库连接，优化查询 |

## 📚 参考资源

- 详细集成指南：[INTEGRATION_GUIDE.md](.github/INTEGRATION_GUIDE.md)
- 项目说明：[README.md](../README.md)
- API文档：[INTEGRATION_GUIDE.md#后端API要求](.github/INTEGRATION_GUIDE.md#后端API要求)

## 🎯 下一步

1. ✅ 前端已准备好连接后端
2. ⏳ 后端实现上述API端点
3. ⏳ 配置CORS允许跨域请求
4. ⏳ 测试各个API端点
5. ⏳ 部署到生产环境

---

**提示**: 前后端完全分离，可以独立开发和部署。确保API契约一致即可。
