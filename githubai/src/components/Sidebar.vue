<template>
  <aside class="sidebar">
    <div class="sidebar-header">
      <h2>矿山检测系统</h2>
    </div>
    <nav class="sidebar-nav">
      <router-link to="/" :class="{ active: $route.path === '/' }" class="nav-item">
        <span class="icon">🗺️</span>
        <span>首页</span>
      </router-link>
      <router-link to="/mine-enterprise" :class="{ active: $route.path === '/mine-enterprise' }" class="nav-item">
        <span class="icon">🏢</span>
        <span>矿企管理</span>
      </router-link>
      <router-link to="/mineral-management" :class="{ active: $route.path === '/mineral-management' }" class="nav-item">
        <span class="icon">⛏️</span>
        <span>矿产管理</span>
      </router-link>
      <router-link to="/employee-management" :class="{ active: $route.path === '/employee-management' }" class="nav-item">
        <span class="icon">👥</span>
        <span>员工管理</span>
      </router-link>
      <router-link to="/electric-vehicle" :class="{ active: $route.path === '/electric-vehicle' }" class="nav-item">
        <span class="icon">🚲</span>
        <span>绿源电动车</span>
      </router-link>
    </nav>
  </aside>
</template>

<script>
export default {
  name: 'Sidebar'
}
</script>

<style scoped>
.sidebar {
  width: 250px;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  overflow-y: auto;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.sidebar-header {
  padding: 20px;
  border-bottom: 2px solid rgba(255, 255, 255, 0.2);
  text-align: center;
}

.sidebar-header h2 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
}

.sidebar-nav {
  padding: 20px 0;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  color: rgba(255, 255, 255, 0.8);
  text-decoration: none;
  transition: all 0.3s ease;
  border-left: 3px solid transparent;
}

.nav-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
  color: white;
  border-left-color: white;
}

.nav-item.active {
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
  border-left-color: #ffd700;
  font-weight: 600;
}

.icon {
  margin-right: 12px;
  font-size: 20px;
}
</style>
