import apiClient from './config'

export const mineralAPI = {
  // 获取所有矿产列表
  getAll() {
    return apiClient.get('/v1/minerals')
  },

  // 按ID获取矿产
  getById(id) {
    return apiClient.get(`/v1/minerals/${id}`)
  },

  // 创建新矿产
  create(data) {
    return apiClient.post('/v1/minerals', data)
  },

  // 更新矿产
  update(id, data) {
    return apiClient.put(`/v1/minerals/${id}`, data)
  },

  // 删除矿产
  delete(id) {
    return apiClient.delete(`/v1/minerals/${id}`)
  }
}

export default mineralAPI
