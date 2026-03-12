# 系统架构设计

## 整体架构

```
┌─────────────────────────────────────────────────────────────────────┐
│                          客户端浏览器                                 │
│                     (http://localhost:5173)                          │
└────────────────┬────────────────────────────────────────────────────┘
                 │
                 │ HTTP/CORS
                 │
┌────────────────▼────────────────────────────────────────────────────┐
│                     Vue 3 前端应用                                   │
├─────────────────────────────────────────────────────────────────────┤
│ • Sidebar.vue              - 导航栏                                  │
│ • Home.vue                 - 首页（地图+污染信息）                   │
│ • MineEnterprise.vue       - 矿企管理                               │
│ • MineralManagement.vue    - 矿产管理                               │
│ • EmployeeManagement.vue   - 员工管理                               │
├─────────────────────────────────────────────────────────────────────┤
│ API服务层 (src/api/)                                                │
│ • config.js       - Axios配置和拦截器                              │
│ • mineEnterprise.js  - 矿企API客户端                               │
│ • mineral.js      - 矿产API客户端                                   │
│ • employee.js     - 员工API客户端                                   │
│ • pollution.js    - 污染数据API客户端                               │
├──────────────────────────────────┬──────────────────────────────────┤
│         构建工具: Vite           │      包管理: npm                  │
└──────────────────────────────────┴──────────────────────────────────┘
                           │
                           │ REST API (JSON)
                           │ http://localhost:8080/api/*
                           │
┌────────────────▼────────────────────────────────────────────────────┐
│                   Spring Boot 后端应用                               │
│                (http://localhost:8080)                              │
├─────────────────────────────────────────────────────────────────────┤
│ REST Controller层                                                    │
│ • MineEnterpriseController   - /api/mine-enterprises/*               │
│ • MineralController          - /api/minerals/*                       │
│ • EmployeeController         - /api/employees/*                      │
│ • PollutionController        - /api/pollution-data/*                 │
├─────────────────────────────────────────────────────────────────────┤
│ Service层                                                            │
│ • MineEnterpriseService      - 矿企业务逻辑                         │
│ • MineralService             - 矿产业务逻辑                         │
│ • EmployeeService            - 员工业务逻辑                         │
│ • PollutionService           - 污染数据业务逻辑                     │
├─────────────────────────────────────────────────────────────────────┤
│ Repository层 (JPA/Mybatis)                                          │
│ • MineEnterpriseRepository                                           │
│ • MineralRepository                                                  │
│ • EmployeeRepository                                                 │
│ • PollutionRepository                                                │
├──────────────────────────────────┬──────────────────────────────────┤
│         框架: Spring Boot         │   ORM: JPA/Hiberate or Mybatis   │
└──────────────────────────────────┴──────────────────────────────────┘
                           │
                           │ SQL
                           │
┌────────────────▼────────────────────────────────────────────────────┐
│                      MySQL数据库                                    │
│              (Server: localhost:3306)                               │
├─────────────────────────────────────────────────────────────────────┤
│ 表数据结构                                                          │
│ • mine_enterprises    - 矿企表                                      │
│ • minerals           - 矿产表                                       │
│ • employees          - 员工表                                       │
│ • pollution_data     - 污染数据表                                   │
└─────────────────────────────────────────────────────────────────────┘
```

## 数据流向

### 1. 查询流程 (读取数据)

```
User Action (点击"矿企管理")
    ↓
MineEnterprise.vue mounted()
    ↓
调用 mineEnterpriseAPI.getAll()
    ↓
axios GET http://localhost:8080/api/mine-enterprises
    ↓
[网络传输]
    ↓
MineEnterpriseController.getAll()
    ↓
mineEnterpriseService.findAll()
    ↓
mineEnterpriseRepository.findAll()
    ↓
SELECT * FROM mine_enterprises
    ↓
[返回数据]
    ↓
JSON Response
    ↓
[网络传输]
    ↓
前端接收数据
    ↓
this.companies = response.data
    ↓
Vue渲染列表
    ↓
页面显示矿企列表
```

### 2. 创建流程 (新增数据)

```
User Action (点击"添加矿企"按钮)
    ↓
Modal表单显示
    ↓
User填写表单并提交
    ↓
调用 mineEnterpriseAPI.create(formData)
    ↓
axios POST http://localhost:8080/api/mine-enterprises
Request Body: { name, location, contact, phone, status }
    ↓
[网络传输]
    ↓
MineEnterpriseController.create(@RequestBody dto)
    ↓
mineEnterpriseService.save(entity)
    ↓
mineEnterpriseRepository.save(entity)
    ↓
INSERT INTO mine_enterprises (name, location, ...)
    ↓
[返回新记录]
    ↓
JSON Response (201 Created)
    ↓
[网络传输]
    ↓
前端closeForm()
    ↓
调用 fetchCompanies() 刷新列表
    ↓
页面更新显示新矿企
```

### 3. 更新流程 (编辑数据)

```
User Action (点击编辑按钮)
    ↓
editCompany(company)
    ↓
formData = company
showAddForm = true
    ↓
Modal显示编辑表单
    ↓
User修改字段并提交
    ↓
调用 mineEnterpriseAPI.update(id, formData)
    ↓
axios PUT http://localhost:8080/api/mine-enterprises/{id}
Request Body: { name, location, contact, phone, status }
    ↓
[网络传输]
    ↓
MineEnterpriseController.update(id, dto)
    ↓
mineEnterpriseService.update(id, dto)
    ↓
mineEnterpriseRepository.save(entity)
    ↓
UPDATE mine_enterprises SET ...
    ↓
JSON Response (200 OK)
    ↓
[网络传输]
    ↓
前端 fetchCompanies() 刷新列表
    ↓
页面更新显示修改后的矿企
```

### 4. 删除流程 (删除数据)

```
User Action (点击删除按钮)
    ↓
confirm('确定要删除？')
    ↓
调用 mineEnterpriseAPI.delete(id)
    ↓
axios DELETE http://localhost:8080/api/mine-enterprises/{id}
    ↓
[网络传输]
    ↓
MineEnterpriseController.delete(id)
    ↓
mineEnterpriseService.delete(id)
    ↓
mineEnterpriseRepository.deleteById(id)
    ↓
DELETE FROM mine_enterprises WHERE id = {id}
    ↓
JSON Response (204 No Content)
    ↓
[网络传输]
    ↓
前端 fetchCompanies() 刷新列表
    ↓
页面移除已删除的矿企
```

## HTTP请求/响应示例

### 例1：获取矿企列表

**请求**:
```
GET /api/mine-enterprises HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**响应**:
```json
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "id": "ME001",
      "name": "中国神华矿业有限公司",
      "location": "陕西省榆林市",
      "contact": "李明",
      "phone": "029-8765-4321",
      "status": "active"
    },
    {
      "id": "ME002",
      "name": "大同煤矿集团",
      "location": "山西省大同市",
      "contact": "王福成",
      "phone": "0352-5123-456",
      "status": "active"
    }
  ]
}
```

### 例2：创建矿企

**请求**:
```
POST /api/mine-enterprises HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "name": "新矿企名称",
  "location": "陕西省",
  "contact": "张三",
  "phone": "13800000000",
  "status": "active"
}
```

**响应**:
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "id": "ME005",
    "name": "新矿企名称",
    "location": "陕西省",
    "contact": "张三",
    "phone": "13800000000",
    "status": "active"
  }
}
```

### 例3：更新矿企

**请求**:
```
PUT /api/mine-enterprises/ME001 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "name": "中国神华矿业（修改）",
  "location": "陕西省榆林市",
  "contact": "李明",
  "phone": "029-8765-4321",
  "status": "maintenance"
}
```

**响应**:
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "id": "ME001",
    "name": "中国神华矿业（修改）",
    "location": "陕西省榆林市",
    "contact": "李明",
    "phone": "029-8765-4321",
    "status": "maintenance"
  }
}
```

### 例4：删除矿企

**请求**:
```
DELETE /api/mine-enterprises/ME005 HTTP/1.1
Host: localhost:8080
```

**响应**:
```json
{
  "code": 0,
  "message": "success",
  "data": null
}
```

## 数据库表结构

### 矿企表 (mine_enterprises)
```sql
CREATE TABLE mine_enterprises (
  id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  location VARCHAR(200),
  contact VARCHAR(100),
  phone VARCHAR(20),
  status VARCHAR(20),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
```

### 矿产表 (minerals)
```sql
CREATE TABLE minerals (
  id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  type VARCHAR(100),
  company VARCHAR(200),
  reserves DECIMAL(10,2),
  grade DECIMAL(5,2),
  status VARCHAR(20),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
```

### 员工表 (employees)
```sql
CREATE TABLE employees (
  id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  department VARCHAR(100),
  position VARCHAR(100),
  location VARCHAR(200),
  phone VARCHAR(20),
  join_date DATE,
  status VARCHAR(20),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
```

### 污染数据表 (pollution_data)
```sql
CREATE TABLE pollution_data (
  id VARCHAR(50) PRIMARY KEY,
  mine_id VARCHAR(50),
  pollution_index DECIMAL(5,2),
  pm25 DECIMAL(5,2),
  pm10 DECIMAL(5,2),
  so2 DECIMAL(5,2),
  no2 DECIMAL(5,2),
  timestamp TIMESTAMP,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (mine_id) REFERENCES mine_enterprises(id)
);
```

## 技术栈总结

| 层级 | 技术 | 版本 |
|------|------|------|
| 前端框架 | Vue 3 | 3.x |
| 前端构建 | Vite | 最新 |
| 前端路由 | Vue Router | 4.x |
| 前端HTTP | Axios | 1.x |
| 后端框架 | Spring Boot | 2.7+ |
| ORM框架 | JPA/Mybatis | - |
| 数据库 | MySQL | 5.7+ |
| 缓存 | Redis (可选) | 6.x+ |

## 部署架构

```
┌────────────────────────────────────────────────────────────┐
│                    生产环境                                 │
├────────────────────────────────────────────────────────────┤
│                                                            │
│  ┌──────────────────┐      ┌──────────────────┐           │
│  │  CDN / Nginx     │      │  Apache / Nginx  │           │
│  │ (前端静态资源)   │      │  (反向代理)      │           │
│  └────────┬─────────┘      └────────┬─────────┘           │
│           │                         │                     │
│     http://domain.com         http://api.domain.com       │
│           │                         │                     │
│  ┌────────▼──────────────────┬─────▼─────────────────┐   │
│  │                           │                       │   │
│  │   前端静态文件            │  Spring Boot App      │   │
│  │   (HTML/JS/CSS)           │                       │   │
│  │                           │  • Controller         │   │
│  │                           │  • Service            │   │
│  │                           │  • Repository         │   │
│  │                           │                       │   │
│  └────────────────┬──────────┴───────────┬───────────┘   │
│                   │                      │                │
│                   └──────────┬───────────┘                │
│                              │                           │
│                    ┌─────────▼────────┐                 │
│                    │   MySQL数据库    │                 │
│                    │  (云RDS或自建)   │                 │
│                    └──────────────────┘                 │
│                                                        │
└────────────────────────────────────────────────────────┘
```

---

**注意**: 确保前后端API契约一致，前端按照上述API调用方式进行开发。
