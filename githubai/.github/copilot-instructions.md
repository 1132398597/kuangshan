# 中国矿山检测系统 - 前端开发指南

## 项目概述

这是一个基于 Vue 3 + Vite 的中国矿山检测系统前端应用。系统提供了矿山企业管理、矿产管理、员工管理以及实时污染信息监测功能。

## 项目结构

```
src/
├── components/          # Vue 组件
│   ├── Sidebar.vue     # 左侧导航栏
│   ├── ChinaMap.vue    # 中国地图展示和矿山选择
│   ├── PollutionPanel.vue # 污染信息面板
│   └── chinaMap.json   # 地图数据
├── views/              # 页面组件
│   ├── Home.vue        # 首页（地图+污染信息）
│   ├── MineEnterprise.vue      # 矿企管理页面
│   ├── MineralManagement.vue   # 矿产管理页面
│   └── EmployeeManagement.vue  # 员工管理页面
├── router/
│   └── index.js        # 路由配置
├── api/                # API 接口（预留）
├── App.vue            # 根组件
├── main.js            # 应用入口
└── style.css          # 全局样式
```

## 功能特性

### 1. 首页（地图视图）
- 中国地图展示，可交互式地选择不同矿山
- 污染等级视觉化展示（高/中/低）
- 实时污染数据显示
- 污染指数详细信息（PM2.5, PM10, SO2, NO2）

### 2. 矿企管理
- 矿企信息列表展示
- 添加/编辑/删除矿企
- 企业状态管理（正常/停运/维护）
- 企业基本信息管理

### 3. 矿产管理
- 矿产信息列表展示
- 添加/编辑/删除矿产
- 矿产类型分类（煤炭、铁矿、铜矿等）
- 储量和品位管理
- 开采状态跟踪

### 4. 员工管理
- 员工信息完整管理
- 添加/编辑/删除员工
- 部门和职位管理
- 员工状态追踪（在职/休假/离职）

## 技术栈

- **框架**: Vue 3（Composition API 可选）
- **构建工具**: Vite
- **路由**: Vue Router 4
- **数据可视化**: ECharts 5
- **HTTP 客户端**: Axios（预留）

## 开发指南

### 安装依赖
```bash
npm install
```

### 启动开发服务器
```bash
npm run dev
```
服务器运行在 `http://localhost:5173/`

### 构建生产版本
```bash
npm run build
```

### 预览生产构建
```bash
npm run preview
```

## 组件说明

### Sidebar 导航栏
- 显示系统名称和导航菜单
- 高亮显示当前活跃页面
- 响应式设计，支持所有屏幕尺寸

### ChinaMap 地图组件
- 基于 ECharts 的中国地图
- 支持地图缩放和拖拽
- 显示矿山位置和污染等级
- 点击选择矿山的交互功能

### PollutionPanel 污染信息面板
- 显示选中矿山的污染数据
- 实时更新污染指数
- 详细的污染物浓度信息

## API 预留

`src/api/` 目录已预留用于 API 接口集成。建议创建以下模块：

```javascript
// api/mine.js - 矿企管理接口
// api/mineral.js - 矿产管理接口
// api/employee.js - 员工管理接口
// api/pollution.js - 污染数据接口
```

## 样式设计

### 色彩方案
- 主色: `#667eea` 和 `#764ba2`（紫色渐变）
- 成功: `#44ff44` / `#d4edda`
- 警告: `#ffaa44` / `#fff3cd`
- 危险: `#ff4444` / `#f8d7da`

### 响应式设计
- 移动优先的开发方法
- 支持所有主流浏览器
- 流畅的过渡和动画效果

## 后续开发

### Phase 2 计划
- [ ] 连接真实 API 接口
- [ ] 数据权限管理
- [ ] 用户认证和授权
- [ ] 高级搜索和过滤
- [ ] 数据导出功能

### Phase 3 计划
- [ ] 实时污染数据推送（WebSocket）
- [ ] 预警和告警系统
- [ ] 历史数据分析和趋势图表
- [ ] 用户权限和角色管理

## 故障排除

### 开发服务器卡顿
确保 `node_modules` 已正确安装：
```bash
rm -rf node_modules package-lock.json
npm install
```

### 地图不显示
检查 `src/components/chinaMap.json` 是否存在且格式正确。

### 样式问题
清除浏览器缓存并重新加载：`Ctrl+Shift+R` (Windows) 或 `Cmd+Shift+R` (Mac)

## 贡献指南

欢迎贡献代码！请遵循以下步骤：
1. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
2. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
3. 推送到分支 (`git push origin feature/AmazingFeature`)
4. 开启一个 Pull Request

## 许可证

MIT
