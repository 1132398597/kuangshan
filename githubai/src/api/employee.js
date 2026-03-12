import apiClient from './config'

export const employeeAPI = {
  // 获取所有员工列表
  getAll() {
    return apiClient.get('/v1/employees')
  },

  // 按ID获取员工
  getById(id) {
    return apiClient.get(`/v1/employees/${id}`)
  },

  // 创建新员工
  create(data) {
    return apiClient.post('/v1/employees', data)
  },

  // 更新员工
  update(id, data) {
    return apiClient.put(`/v1/employees/${id}`, data)
  },

  // 删除员工
  delete(id) {
    return apiClient.delete(`/v1/employees/${id}`)
  }
}

export default employeeAPI
