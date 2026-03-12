import apiClient from './config'

export const mineEnterpriseAPI = {
  // 获取所有矿企列表
  getAll() {
    return apiClient.get('/v1/enterprises')
  },

  // 按ID获取矿企
  getById(id) {
    return apiClient.get(`/v1/enterprises/${id}`)
  },

  // 创建新矿企
  create(data) {
    return apiClient.post('/v1/enterprises', data)
  },

  // 更新矿企
  update(id, data) {
    return apiClient.put(`/v1/enterprises/${id}`, data)
  },

  // 删除矿企
  delete(id) {
    return apiClient.delete(`/v1/enterprises/${id}`)
  }
}

export default mineEnterpriseAPI
