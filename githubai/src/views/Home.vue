<template>
  <div class="home-container">
    <div class="map-wrapper">
      <ChinaMap @mine-selected="selectMine" />
    </div>
    <div class="info-wrapper">
      <PollutionPanel :selectedMine="selectedMine" />
    </div>
  </div>
</template>

<script>
import ChinaMap from '../components/ChinaMap.vue'
import PollutionPanel from '../components/PollutionPanel.vue'

export default {
  name: 'Home',
  components: {
    ChinaMap,
    PollutionPanel
  },
  data() {
    return {
      selectedMine: null
    }
  },
  methods: {
    selectMine(mine) {
      this.selectedMine = mine
    }
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
  height: 100vh;
  gap: 20px;
  padding: 20px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.map-wrapper {
  flex: 1;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  overflow: hidden;
}

.info-wrapper {
  width: 350px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}
</style>
