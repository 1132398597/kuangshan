<template>
  <div class="management-container">
    <div class="header">
      <h1>👥 员工管理</h1>
      <button class="btn-primary" @click="showAddForm = true">
        + 添加员工
      </button>
    </div>

    <div class="table-wrapper">
      <table class="management-table">
        <thead>
          <tr>
            <th>员工ID</th>
            <th>员工姓名</th>
            <th>所属部门</th>
            <th>职位</th>
            <th>工作地点</th>
            <th>联系电话</th>
            <th>入职日期</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="employee in employees" :key="employee.id">
            <td>{{ employee.id }}</td>
            <td>{{ employee.name }}</td>
            <td>{{ employee.department }}</td>
            <td>{{ employee.position }}</td>
            <td>{{ employee.location }}</td>
            <td>{{ employee.phone }}</td>
            <td>{{ employee.joinDate }}</td>
            <td>
              <span :class="['status', employee.status]">
                {{ statusMap[employee.status] }}
              </span>
            </td>
            <td>
              <button class="btn-small" @click="editEmployee(employee)">编辑</button>
              <button class="btn-small btn-danger" @click="deleteEmployee(employee.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-if="showAddForm" class="modal-overlay" @click="closeForm">
      <div class="modal" @click.stop>
        <h2>添加新员工</h2>
        <form @submit.prevent="saveEmployee">
          <div class="form-row">
            <div class="form-group">
              <label>员工姓名</label>
              <input v-model="formData.name" type="text" placeholder="请输入员工姓名" required>
            </div>
            <div class="form-group">
              <label>所属部门</label>
              <select v-model="formData.department" required>
                <option value="安全管理">安全管理</option>
                <option value="开采部">开采部</option>
                <option value="运营部">运营部</option>
                <option value="后勤部">后勤部</option>
                <option value="技术部">技术部</option>
              </select>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>职位</label>
              <input v-model="formData.position" type="text" placeholder="请输入职位" required>
            </div>
            <div class="form-group">
              <label>工作地点</label>
              <input v-model="formData.location" type="text" placeholder="请输入工作地点" required>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>联系电话</label>
              <input v-model="formData.phone" type="tel" placeholder="请输入联系电话" required>
            </div>
            <div class="form-group">
              <label>入职日期</label>
              <input v-model="formData.joinDate" type="date" required>
            </div>
          </div>
          <div class="form-group">
            <label>状态</label>
            <select v-model="formData.status" required>
              <option value="working">在职</option>
              <option value="leave">休假</option>
              <option value="resigned">离职</option>
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
import employeeAPI from '../api/employee'

export default {
  name: 'EmployeeManagement',
  data() {
    return {
      showAddForm: false,
      editingId: null,
      loading: false,
      statusMap: {
        working: '在职',
        leave: '休假',
        resigned: '离职'
      },
      formData: {
        name: '',
        department: '安全管理',
        position: '',
        location: '',
        phone: '',
        joinDate: '',
        status: 'working'
      },
      employees: []
    }
  },
  mounted() {
    this.fetchEmployees()
  },
  methods: {
    async fetchEmployees() {
      try {
        this.loading = true
        const response = await employeeAPI.getAll()
        this.employees = response.data || response || []
      } catch (error) {
        console.error('Failed to fetch employees:', error)
        this.$message?.error?.('加载员工列表失败，请检查后端服务')
      } finally {
        this.loading = false
      }
    },
    async saveEmployee() {
      try {
        if (this.editingId) {
          await employeeAPI.update(this.editingId, this.formData)
        } else {
          await employeeAPI.create(this.formData)
        }
        this.closeForm()
        this.fetchEmployees()
      } catch (error) {
        console.error('Failed to save employee:', error)
        alert('保存失败，请检查填写的信息是否正确')
      }
    },
    editEmployee(employee) {
      this.editingId = employee.id
      Object.assign(this.formData, employee)
      this.showAddForm = true
    },
    async deleteEmployee(id) {
      if (confirm('确定要删除这个员工吗？')) {
        try {
          await employeeAPI.delete(id)
          this.fetchEmployees()
        } catch (error) {
          console.error('Failed to delete employee:', error)
          alert('删除失败，请稍后重试')
        }
      }
    },
    closeForm() {
      this.showAddForm = false
      this.editingId = null
      this.formData = {
        name: '',
        department: '安全管理',
        position: '',
        location: '',
        phone: '',
        joinDate: '',
        status: 'working'
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
  overflow: auto;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.management-table {
  width: 100%;
  border-collapse: collapse;
}

.management-table thead {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  position: sticky;
  top: 0;
}

.management-table th,
.management-table td {
  padding: 15px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
  white-space: nowrap;
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

.status.working {
  background-color: #d4edda;
  color: #155724;
}

.status.leave {
  background-color: #fff3cd;
  color: #856404;
}

.status.resigned {
  background-color: #f8d7da;
  color: #721c24;
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
  overflow-y: auto;
}

.modal {
  background: white;
  padding: 30px;
  border-radius: 8px;
  max-width: 600px;
  width: 100%;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  animation: slideUp 0.3s ease-out;
  margin: 30px auto;
}

.modal h2 {
  margin: 0 0 20px 0;
  color: #333;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 15px;
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
  box-sizing: border-box;
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
