<template>
  <div id="main" ref="main" :v-loading="isLoading" class="dashboard-container">
    <ECSInstanceDashboard v-for="(val,key) in ecsData" :key="key" :name="key" :value="val" />
  </div>
</template>
<script>
import { lastDayData } from '../../api/dashboard'
import ECSInstanceDashboard from './components/ECSInstanceDashboard'
export default {
  name: 'Dashboard',
  components: {
    ECSInstanceDashboard
  },
  data() {
    return {
      ecsData: null,
      isLoading: false
    }
  },
  computed: {},
  mounted() {
    const loadingInstance = this.$loading({ target: this.$refs.main })
    this.init(loadingInstance)
  },
  beforeDestroy: function() {
  },
  methods: {
    init(loadingInstance) {
      lastDayData().then(({ data }) => {
        this.ecsData = data
        loadingInstance.close()
      })
    }
  }

}
</script>

<style lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }

    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
</style>
