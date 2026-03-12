import apiClient from './client'

const ENDPOINT = '/mines'

export default {
  // 获取所有矿企
  getAll() {
    return apiClient.get(ENDPOINT)
  },

  // 根据ID获取矿企
  getById(id) {
    return apiClient.get(`${ENDPOINT}/${id}`)
  },

  // 创建矿企
  create(data) {
    return apiClient.post(ENDPOINT, data)
  },

  // 更新矿企
  update(id, data) {
    return apiClient.put(`${ENDPOINT}/${id}`, data)
  },

  // 删除矿企
  delete(id) {
    return apiClient.delete(`${ENDPOINT}/${id}`)
  },

  // 批量获取矿企
  getByIds(ids) {
    return apiClient.post(`${ENDPOINT}/batch`, { ids })
  }
}
