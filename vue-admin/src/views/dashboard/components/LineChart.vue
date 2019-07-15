<template>
  <div ref="chart" class="chart" />
</template>

<script>
import { parseTime } from '@/utils'

export default {
  name: 'LineChart',
  props: ['data'],
  data() {
    const metricData = JSON.parse(this.data.metricData)
    const xAxisData = []
    const Maximum = []
    const Average = []
    const Minimum = []
    for (const index in metricData) {
      const record = metricData[index]
      xAxisData.push(parseTime(record.timestamp, ' {h}:{i}'))
      Maximum.push(record.Maximum)
      Average.push(record.Average)
      Minimum.push(record.Minimum)
    }
    return {
      metricData,
      xAxisData,
      Maximum,
      Average,
      Minimum,
      unit: this.data.unit
    }
  },
  mounted() {
    const echarts = require('echarts')
    const myChart = echarts.init(this.$refs.chart)
    myChart.setOption(this.createChartOption())
  },
  methods: {
    createChartOption() {
      return {
        title: {
          text: this.data.description,
          x: 'center'
        },
        renderer: 'svg',
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          // orient: 'vertical',
          data: ['最高', '平均', '最低'],
          x: 'left'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.xAxisData
        },
        yAxis: {
          name: this.unit,
          type: 'value'
          // min: 'dataMin'
        },
        series: [
          {
            name: '最高',
            smooth: true,
            type: 'line',
            areaStyle: {},
            data: this.Maximum
          },
          {
            name: '平均',
            type: 'line',
            smooth: true,
            areaStyle: {},

            data: this.Average
          },
          {
            name: '最低',
            smooth: true,
            type: 'line',
            areaStyle: {},

            data: this.Minimum
          }
        ]
      }
    }
  }
}
</script>

<style scoped>
  .chart {
    display: flex;
    width: 700px;
    height: 300px;
    margin: 0 auto;
    padding: 45px;
  }
</style>
