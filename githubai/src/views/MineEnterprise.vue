<template>
  <div class="management-container">
    <div class="header">
      <h1>🏢 矿企管理</h1>
      <button class="btn-primary" @click="showAddForm = true">
        + 添加矿企
      </button>
    </div>

    <div class="table-wrapper">
      <table class="management-table">
        <thead>
          <tr>
            <th>企业编号</th>
            <th>企业名称</th>
            <th>所在地区</th>
            <th>联系人</th>
            <th>联系电话</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="company in companies" :key="company.id">
            <td>{{ company.id }}</td>
            <td>{{ company.name }}</td>
            <td>{{ company.location }}</td>
            <td>{{ company.contact }}</td>
            <td>{{ company.phone }}</td>
            <td>
              <span :class="['status', company.status]">
                {{ statusMap[company.status] }}
              </span>
            </td>
            <td>
              <button class="btn-small" @click="editCompany(company)">编辑</button>
              <button class="btn-small btn-danger" @click="deleteCompany(company.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-if="showAddForm" class="modal-overlay" @click="closeForm">
      <div class="modal" @click.stop>
        <h2>添加新矿企</h2>
        <form @submit.prevent="saveCompany">
          <div class="form-group">
            <label>企业名称</label>
            <input v-model="formData.name" type="text" placeholder="请输入企业名称" required>
          </div>
          <div class="form-group">
            <label>所在地区</label>
            <input v-model="formData.location" type="text" placeholder="请输入所在地区" required>
          </div>
          <div class="form-group">
            <label>联系人</label>
            <input v-model="formData.contact" type="text" placeholder="请输入联系人" required>
          </div>
          <div class="form-group">
            <label>联系电话</label>
            <input v-model="formData.phone" type="tel" placeholder="请输入联系电话" required>
          </div>
          <div class="form-group">
            <label>状态</label>
            <select v-model="formData.status" required>
              <option value="active">正常</option>
              <option value="inactive">停运</option>
              <option value="maintenance">维护</option>
            </select>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn-primary">保存</button>
            <button type="button" class="btn-secondary" @click="closeForm">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import mineEnterpriseAPI from '../api/mineEnterprise'

export default {
  name: 'MineEnterprise',
  data() {
    return {
      showAddForm: false,
      editingId: null,
      loading: false,
      statusMap: {
        active: '正常',
        inactive: '停运',
        maintenance: '维护'
      },
      formData: {
        name: '',
        location: '',
        contact: '',
        phone: '',
        status: 'active'
      },
      companies: []
    }
  },
  mounted() {
    this.fetchCompanies()
  },
  methods: {
    async fetchCompanies() {
      try {
        this.loading = true
        const response = await mineEnterpriseAPI.getAll()
        this.companies = response.data || response || []
      } catch (error) {
        console.error('Failed to fetch companies:', error)
        this.$message?.error?.('加载矿企列表失败，请检查后端服务')
      } finally {
        this.loading = false
      }
    },
    async saveCompany() {
      try {
        if (this.editingId) {
          await mineEnterpriseAPI.update(this.editingId, this.formData)
        } else {
          await mineEnterpriseAPI.create(this.formData)
        }
        this.closeForm()
        this.fetchCompanies()
      } catch (error) {
        console.error('Failed to save company:', error)
        alert('保存失败，请检查填写的信息是否正确')
      }
    },
    editCompany(company) {
      this.editingId = company.id
      Object.assign(this.formData, company)
      this.showAddForm = true
    },
    async deleteCompany(id) {
      if (confirm('确定要删除这个矿企吗？')) {
        try {
          await mineEnterpriseAPI.delete(id)
          this.fetchCompanies()
        } catch (error) {
          console.error('Failed to delete company:', error)
          alert('删除失败，请稍后重试')
        }
      }
    },
    closeForm() {
      this.showAddForm = false
      this.editingId = null
      this.formData = {
        name: '',
        location: '',
        contact: '',
        phone: '',
        status: 'active'
      }
    }
  }
}
</script>

<style scoped>
.management-container {
  padding: 30px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  min-height: 100vh;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.header h1 {
  margin: 0;
  color: #333;
}

.table-wrapper {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.management-table {
  width: 100%;
  border-collapse: collapse;
}

.management-table thead {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.management-table th,
.management-table td {
  padding: 15px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
}

.management-table tr:hover {
  background-color: #f9f9f9;
}

.status {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
}

.status.active {
  background-color: #d4edda;
  color: #155724;
}

.status.inactive {
  background-color: #f8d7da;
  color: #721c24;
}

.status.maintenance {
  background-color: #fff3cd;
  color: #856404;
}

.btn-primary,
.btn-secondary {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.btn-secondary {
  background: #f0f0f0;
  color: #333;
}

.btn-secondary:hover {
  background: #e0e0e0;
}

.btn-small {
  padding: 6px 12px;
  margin-right: 8px;
  border: none;
  border-radius: 4px;
  background: #667eea;
  color: white;
  cursor: pointer;
  font-size: 12px;
  transition: all 0.3s ease;
}

.btn-small:hover {
  background: #5568d3;
}

.btn-danger {
  background: #ff7777;
}

.btn-danger:hover {
  background: #ff5555;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal {
  background: white;
  padding: 30px;
  border-radius: 8px;
  max-width: 500px;
  width: 100%;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  animation: slideUp 0.3s ease-out;
}

.modal h2 {
  margin: 0 0 20px 0;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
  color: #333;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.form-actions {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  margin-top: 30px;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
