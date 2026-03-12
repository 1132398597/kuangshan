import apiClient from './config'

export const pollutionAPI = {
  // 获取所有污染数据
  getAll() {
    return apiClient.get('/v1/pollution')
  },

  // 按矿山ID获取污染数据
  getByMineId(mineId) {
    return apiClient.get(`/v1/pollution/enterprise/${mineId}`)
  },

  // 按ID获取污染数据
  getById(id) {
    return apiClient.get(`/v1/pollution/${id}`)
  },

  // 创建新污染数据
  create(data) {
    return apiClient.post('/v1/pollution', data)
  },

  // 更新污染数据
  update(id, data) {
    return apiClient.put(`/v1/pollution/${id}`, data)
  },

  // 删除污染数据
  delete(id) {
    return apiClient.delete(`/v1/pollution/${id}`)
  }
}

export default pollutionAPI
