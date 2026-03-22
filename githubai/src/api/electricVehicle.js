import apiClient from './config'

export const electricVehicleAPI = {
  // 获取所有绿源电动车列表
  getAll() {
    return apiClient.get('/v1/electric-vehicles')
  },

  // 按ID获取电动车
  getById(id) {
    return apiClient.get(`/v1/electric-vehicles/${id}`)
  },

  // 创建新电动车
  create(data) {
    return apiClient.post('/v1/electric-vehicles', data)
  },

  // 更新电动车
  update(id, data) {
    return apiClient.put(`/v1/electric-vehicles/${id}`, data)
  },

  // 删除电动车
  delete(id) {
    return apiClient.delete(`/v1/electric-vehicles/${id}`)
  }
}

export default electricVehicleAPI
