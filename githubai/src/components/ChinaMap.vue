<template>
  <div class="map-container">
    <div ref="mapElement" class="map-element"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import chinaMapGeoJson from './chinaMap.json'
import mineEnterpriseAPI from '../api/mineEnterprise'
import pollutionAPI from '../api/pollution'

export default {
  name: 'ChinaMap',
  data() {
    return {
      chart: null,
      selectedMine: null,
      mines: [],
      pollutionData: {},
      loading: false
    }
  },
  mounted() {
    this.initMap()
    this.fetchData()
  },
  methods: {
    async fetchData() {
      try {
        this.loading = true
        // 获取矿企数据
        const minesResponse = await mineEnterpriseAPI.getAll()
        const minesData = minesResponse.data || minesResponse || []
        
        // 获取污染数据
        const pollutionResponse = await pollutionAPI.getAll()
        const pollutions = pollutionResponse.data || pollutionResponse || []
        
        // 构建污染数据映射
        this.pollutionData = {}
        pollutions.forEach(p => {
          if (p.mineId) {
            this.pollutionData[p.mineId] = p
          }
        })
        
        // 合并矿企和污染数据
        this.mines = minesData.map(mine => {
          const pollution = this.pollutionData[mine.id]
          const pollutionValue = pollution?.pollutionIndex || 50
          return {
            id: mine.id,
            name: mine.name,
            coord: mine.coordinates ? mine.coordinates.split(',').map(Number) : [110, 40],
            pollution: this.getPollutionLevel(pollutionValue),
            value: pollutionValue,
            location: mine.location
          }
        })
        
        this.drawMap()
      } catch (error) {
        console.error('Failed to fetch data:', error)
        // 如果API失败，使用示例数据
        this.initWithMockData()
      } finally {
        this.loading = false
      }
    },
    initWithMockData() {
      this.mines = [
        {
          id: 'ME001',
          name: '华东矿山',
          coord: [120.15, 30.27],
          pollution: 'high',
          value: 85
        },
        {
          id: 'ME002',
          name: '晋中矿区',
          coord: [112.5, 37.87],
          pollution: 'medium',
          value: 65
        },
        {
          id: 'ME003',
          name: '神华矿点',
          coord: [110.0, 40.8],
          pollution: 'low',
          value: 40
        },
        {
          id: 'ME004',
          name: '兖州煤矿',
          coord: [116.8, 35.5],
          pollution: 'high',
          value: 75
        },
        {
          id: 'ME005',
          name: '贵州矿产',
          coord: [106.7, 26.6],
          pollution: 'medium',
          value: 55
        }
      ]
      this.drawMap()
    },
    getPollutionLevel(value) {
      if (value >= 75) return 'high'
      if (value >= 50) return 'medium'
      return 'low'
    },
    initMap() {
      echarts.registerMap('china', chinaMapGeoJson)
      this.chart = echarts.init(this.$refs.mapElement)
      window.addEventListener('resize', this.handleResize)
    },
    drawMap() {
      if (!this.chart) return
      
      const option = {
        title: {
          text: '中国矿山监测分布',
          left: 'center',
          top: 20,
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold',
            color: '#333'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            if (params.componentType === 'series') {
              return `${params.data.name}<br/>污染指数: ${params.data.value}`
            }
            return params.name
          }
        },
        geo: {
          map: 'china',
          roam: true,
          label: {
            emphasis: {
              show: false
            }
          },
          itemStyle: {
            normal: {
              areaColor: '#f3f3f3',
              borderColor: '#999'
            },
            emphasis: {
              areaColor: '#e0e0e0'
            }
          }
        },
        series: [
          {
            name: '污染指数',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            data: this.mines.sort((a, b) => b.value - a.value),
            symbolSize: (val) => val / 10 + 5,
            showEffectOn: 'render',
            rippleEffect: {
              brushType: 'stroke',
              scale: 2.5,
              period: 4
            },
            itemStyle: {
              normal: {
                color: (params) => {
                  const pollution = params.data.pollution
                  if (pollution === 'high') return '#ff4444'
                  if (pollution === 'medium') return '#ffaa44'
                  return '#44ff44'
                },
                shadowBlur: 10,
                shadowColor: '#333'
              }
            },
            zlevel: 1
          }
        ]
      }
      this.chart.setOption(option)
      this.chart.on('click', this.handleMapClick)
    },
    handleMapClick(params) {
      if (params.componentType === 'series') {
        this.selectedMine = params.data
        this.$emit('mine-selected', params.data)
      }
    },
    handleResize() {
      if (this.chart) {
        this.chart.resize()
      }
    }
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.handleResize)
    if (this.chart) {
      this.chart.dispose()
    }
  }
}
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 100%;
}

.map-element {
  width: 100%;
  height: 100%;
}
</style>
