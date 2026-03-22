<template>
  <div class="management-container">
    <div class="header">
      <h1>🚲 绿源电动车管理</h1>
      <button class="btn-primary" @click="showAddForm = true">
        + 添加电动车
      </button>
    </div>

    <div class="table-wrapper">
      <table class="management-table">
        <thead>
          <tr>
            <th>车辆编号</th>
            <th>品牌</th>
            <th>型号</th>
            <th>车牌号</th>
            <th>驾驶员</th>
            <th>电池容量(kWh)</th>
            <th>购买日期</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="vehicle in vehicles" :key="vehicle.id">
            <td>{{ vehicle.vehicleCode }}</td>
            <td>{{ vehicle.brand }}</td>
            <td>{{ vehicle.model }}</td>
            <td>{{ vehicle.licensePlate }}</td>
            <td>{{ vehicle.driverName }}</td>
            <td>{{ vehicle.batteryCapacity }}</td>
            <td>{{ vehicle.purchaseDate }}</td>
            <td>
              <span :class="['status', vehicle.status]">
                {{ statusMap[vehicle.status] }}
              </span>
            </td>
            <td>
              <button class="btn-small" @click="editVehicle(vehicle)">编辑</button>
              <button class="btn-small btn-danger" @click="deleteVehicle(vehicle.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-if="showAddForm" class="modal-overlay" @click="closeForm">
      <div class="modal" @click.stop>
        <h2>{{ editingId ? '编辑电动车' : '添加新电动车' }}</h2>
        <form @submit.prevent="saveVehicle">
          <div class="form-row">
            <div class="form-group">
              <label>车辆编号</label>
              <input v-model="formData.vehicleCode" type="text" placeholder="请输入车辆编号" required>
            </div>
            <div class="form-group">
              <label>品牌</label>
              <input v-model="formData.brand" type="text" placeholder="请输入品牌" required>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>型号</label>
              <input v-model="formData.model" type="text" placeholder="请输入型号">
            </div>
            <div class="form-group">
              <label>车牌号</label>
              <input v-model="formData.licensePlate" type="text" placeholder="请输入车牌号">
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>驾驶员</label>
              <input v-model="formData.driverName" type="text" placeholder="请输入驾驶员姓名">
            </div>
            <div class="form-group">
              <label>电池容量(kWh)</label>
              <input v-model.number="formData.batteryCapacity" type="number" step="0.1" placeholder="请输入电池容量">
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>购买日期</label>
              <input v-model="formData.purchaseDate" type="date">
            </div>
            <div class="form-group">
              <label>状态</label>
              <select v-model="formData.status" required>
                <option value="ACTIVE">正常</option>
                <option value="MAINTENANCE">维修中</option>
                <option value="SCRAPPED">已报废</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label>备注</label>
            <input v-model="formData.remarks" type="text" placeholder="请输入备注">
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
import electricVehicleAPI from '../api/electricVehicle'

export default {
  name: 'ElectricVehicleManagement',
  data() {
    return {
      showAddForm: false,
      editingId: null,
      loading: false,
      statusMap: {
        ACTIVE: '正常',
        MAINTENANCE: '维修中',
        SCRAPPED: '已报废'
      },
      formData: {
        vehicleCode: '',
        brand: '绿源',
        model: '',
        licensePlate: '',
        driverName: '',
        batteryCapacity: '',
        purchaseDate: '',
        status: 'ACTIVE',
        remarks: ''
      },
      vehicles: []
    }
  },
  mounted() {
    this.fetchVehicles()
  },
  methods: {
    async fetchVehicles() {
      try {
        this.loading = true
        const response = await electricVehicleAPI.getAll()
        this.vehicles = response.data || response || []
      } catch (error) {
        console.error('Failed to fetch vehicles:', error)
        this.$message?.error?.('加载电动车列表失败，请检查后端服务')
      } finally {
        this.loading = false
      }
    },
    async saveVehicle() {
      try {
        if (this.editingId) {
          await electricVehicleAPI.update(this.editingId, this.formData)
        } else {
          await electricVehicleAPI.create(this.formData)
        }
        this.closeForm()
        this.fetchVehicles()
      } catch (error) {
        console.error('Failed to save vehicle:', error)
        alert('保存失败，请检查填写的信息是否正确')
      }
    },
    editVehicle(vehicle) {
      this.editingId = vehicle.id
      Object.assign(this.formData, vehicle)
      this.showAddForm = true
    },
    async deleteVehicle(id) {
      if (confirm('确定要删除这辆电动车吗？')) {
        try {
          await electricVehicleAPI.delete(id)
          this.fetchVehicles()
        } catch (error) {
          console.error('Failed to delete vehicle:', error)
          alert('删除失败，请稍后重试')
        }
      }
    },
    closeForm() {
      this.showAddForm = false
      this.editingId = null
      this.formData = {
        vehicleCode: '',
        brand: '绿源',
        model: '',
        licensePlate: '',
        driverName: '',
        batteryCapacity: '',
        purchaseDate: '',
        status: 'ACTIVE',
        remarks: ''
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

.status.ACTIVE {
  background-color: #d4edda;
  color: #155724;
}

.status.MAINTENANCE {
  background-color: #fff3cd;
  color: #856404;
}

.status.SCRAPPED {
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
