<template>
  <div class="instance-container">
    <el-row>
      <div>{{ instance.instanceName }}</div>
      <p />
      <el-switch
        v-model="isGroup"
        active-text="开启图表联动"
        inactive-text="关闭"
      />
    </el-row>
    <el-row :gutter="40">
      <el-col v-for="(val,key) in items" :key="key" ref="charts" :xs="24" :lg="11">
        <LineChart :data="val" :group="EcsGroup" class="line-chart" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
// TODO 多表联动
import LineChart from './LineChart'
const echarts = require('echarts')

export default {
  name: 'ECSInstanceDashboard',
  components: {
    LineChart
  },

  props: ['name', 'value'],
  data() {
    return {
      EcsGroup: 'ecs-group',
      isGroup: false,
      instance: {
        instanceName: this.name,
        imageId: this.value.imageId,
        internetMaxBandwidthIn: this.value.internetMaxBandwidthIn,
        internetMaxBandwidthOut: this.value.internetMaxBandwidthOut,
        publicIpAddress: this.value.publicIpAddress,
        status: this.value.status,
        zoneId: this.value.zoneId
      },
      items: {
        CPUUtilization: this.value.items.CPUUtilization, // CUP 百分比
        cpu_user: this.value.items.cpu_user, // CUP 当前用户CPU百分比

        memory_usedutilization: this.value.items.memory_usedutilization, // 内存使用率
        net_tcpconnection: this.value.items.net_tcpconnection, // TCP连接数

        networkin_rate: this.value.items.networkin_rate, // 网卡的上行带宽
        networkout_rate: this.value.items.networkout_rate, // 网卡的下行带宽

        VPC_PublicIP_InternetInRate: this.value.items.VPC_PublicIP_InternetInRate, // 专有网络公网流出带宽
        VPC_PublicIP_InternetOutRate: this.value.items.VPC_PublicIP_InternetOutRate, // 专有网络公网流出带宽

        DiskReadIOPS: this.value.items.DiskReadIOPS, // 系统磁盘读IOPS
        DiskWriteIOPS: this.value.items.DiskWriteIOPS, // 系统磁盘写IOPS

        IntranetInRate: this.value.items.IntranetInRate,
        IntranetOutRate: this.value.items.IntranetOutRate,

        diskusage_utilization: this.value.items.diskusage_utilization, // 银盘使用率
        load_5m: this.value.items.load_5m, // 过去5分钟的系统平均负载

        InternetInRate: this.value.items.InternetInRate, // 公网流入带宽
        InternetOutRate: this.value.items.InternetOutRate // 公网流出带宽
      }
    }
  },
  watch: {
    isGroup: function() {
      this.groupCharts(this.isGroup)
    }
  },
  mounted() {
  },

  methods: {
    groupCharts(isGroup) {
      if (isGroup) {
        echarts.connect(this.EcsGroup)
      } else {
        echarts.disconnect(this.EcsGroup)
      }
    }
  }
}
</script>

<style scoped>
  .instance-container {
    max-width: 1440px;
    min-width: 500px;
  }

  .line-chart {
    /*width: 300px;*/
    height: 350px;
  }

</style>
