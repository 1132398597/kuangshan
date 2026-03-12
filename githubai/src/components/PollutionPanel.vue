<template>
  <div class="pollution-panel">
    <h3>实时污染信息</h3>
    <div v-if="selectedMine" class="mine-info">
      <div class="info-item">
        <span class="label">矿山名称：</span>
        <span class="value">{{ selectedMine.name }}</span>
      </div>
      <div class="info-item">
        <span class="label">污染等级：</span>
        <span :class="['badge', selectedMine.pollution]">
          {{ pollutionLevel[selectedMine.pollution] }}
        </span>
      </div>
      <div class="info-item">
        <span class="label">污染指数：</span>
        <span class="value">{{ selectedMine.value }}</span>
      </div>
      <div class="pollution-details">
        <div class="detail-item">
          <span>PM2.5:</span>
          <span>{{ selectedMine.value * 0.8 }} μg/m³</span>
        </div>
        <div class="detail-item">
          <span>PM10:</span>
          <span>{{ selectedMine.value * 1.2 }} μg/m³</span>
        </div>
        <div class="detail-item">
          <span>SO2:</span>
          <span>{{ selectedMine.value * 0.6 }} ppb</span>
        </div>
        <div class="detail-item">
          <span>NO2:</span>
          <span>{{ selectedMine.value * 0.7 }} ppb</span>
        </div>
      </div>
    </div>
    <div v-else class="empty-state">
      <p>请在地图上选择矿山查看污染信息</p>
    </div>
  </div>
</template>

<script>
import pollutionAPI from '../api/pollution'

export default {
  name: 'PollutionPanel',
  props: {
    selectedMine: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      pollutionLevel: {
        low: '低',
        medium: '中',
        high: '高'
      },
      detailData: null,
      loading: false
    }
  },
  watch: {
    selectedMine(newMine) {
      if (newMine && newMine.id) {
        this.fetchPollutionDetail(newMine.id)
      }
    }
  },
  methods: {
    async fetchPollutionDetail(mineId) {
      try {
        this.loading = true
        const response = await pollutionAPI.getByMineId(mineId)
        this.detailData = response.data || response
      } catch (error) {
        console.error('Failed to fetch pollution detail:', error)
        this.detailData = null
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.pollution-panel {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  max-width: 400px;
}

.pollution-panel h3 {
  margin: 0 0 20px 0;
  color: #333;
  font-size: 16px;
  border-bottom: 2px solid #667eea;
  padding-bottom: 10px;
}

.mine-info {
  animation: slideIn 0.3s ease-out;
}

.info-item {
  display: flex;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.label {
  font-weight: 600;
  color: #666;
}

.value {
  color: #333;
}

.badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-weight: 600;
  font-size: 12px;
}

.badge.low {
  background-color: #d4edda;
  color: #155724;
}

.badge.medium {
  background-color: #fff3cd;
  color: #856404;
}

.badge.high {
  background-color: #f8d7da;
  color: #721c24;
}

.pollution-details {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 2px solid #f0f0f0;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  font-size: 14px;
}

.detail-item span:first-child {
  color: #666;
  font-weight: 600;
}

.detail-item span:last-child {
  color: #ff7777;
  font-weight: 600;
}

.empty-state {
  text-align: center;
  padding: 40px 20px;
  color: #999;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
