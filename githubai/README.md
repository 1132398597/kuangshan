# 中国矿山检测系统

一个基于 Vue 3 + Vite 的矿山企业管理和污染监测系统前端应用。

## 功能特性

- 🗺️ **交互式地图** - 中国地图展示和矿山选择
- 📊 **实时监测** - 污染数据实时显示和更新
- 🏢 **矿企管理** - 完整的矿山企业信息管理
- ⛏️ **矿产管理** - 矿产资源及开采状态管理
- 👥 **员工管理** - 员工信息和部门管理系统

## 技术栈

- Vue 3
- Vite
- Vue Router 4
- ECharts 5
- Axios

## 快速开始

### 安装依赖

```bash
npm install
```

### 启动开发服务器

```bash
npm run dev
```

服务器将在 `http://localhost:5173/` 启动

### 构建生产版本

```bash
npm run build
```

## 项目结构

```
src/
├── components/          # Vue 组件
│   ├── Sidebar.vue     # 导航栏
│   ├── ChinaMap.vue    # 地图组件
│   ├── PollutionPanel.vue # 污染信息面板
│   └── chinaMap.json   # 地图数据
├── views/              # 页面
│   ├── Home.vue        # 首页
│   ├── MineEnterprise.vue      # 矿企管理
│   ├── MineralManagement.vue   # 矿产管理
│   └── EmployeeManagement.vue  # 员工管理
├── router/
│   └── index.js        # 路由配置
├── api/                # API 接口
├── App.vue            # 根组件
├── main.js            # 应用入口
└── style.css          # 全局样式
```

## 主要功能说明

### 首页（Home）
- 交互式地图展示
- 矿山选择功能
- 污染信息实时显示

### 矿企管理
- 矿企列表展示
- 新增、编辑、删除矿企
- 企业状态管理

### 矿产管理
- 矿产信息列表
- 矿产类型分类
- 储量和品位管理
- 开采状态跟踪

### 员工管理
- 员工信息管理
- 部门和职位管理
- 员工状态追踪

## 色彩方案

- 主色调: 紫色渐变 `#667eea` → `#764ba2`
- 成功: `#44ff44` / `#d4edda`
- 警告: `#ffaa44` / `#fff3cd`
- 错误: `#ff4444` / `#f8d7da`

## 开发建议

1. 组件尽量保持单一职责
2. 样式使用 Scoped CSS 避免污染
3. 路由懒加载优化性能
4. 环境变量配置在 `.env` 文件中

## 后续开发计划

- [ ] 接入真实后端 API
- [ ] 用户认证和授权
- [ ] WebSocket 实时数据更新
- [ ] 预警告警系统
- [ ] 数据分析和报表

## 许可证

MIT

## 开发者

Created with ❤️ for mining enterprise management

