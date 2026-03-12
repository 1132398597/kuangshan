import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import MineEnterprise from '../views/MineEnterprise.vue'
import MineralManagement from '../views/MineralManagement.vue'
import EmployeeManagement from '../views/EmployeeManagement.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/mine-enterprise',
    name: 'MineEnterprise',
    component: MineEnterprise
  },
  {
    path: '/mineral-management',
    name: 'MineralManagement',
    component: MineralManagement
  },
  {
    path: '/employee-management',
    name: 'EmployeeManagement',
    component: EmployeeManagement
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
