<template>
  <div class="dashboard-container">
    {{ ecsData }}
  </div>
</template>
<script>
import { last5MinData } from '../../api/dashboard'

export default {
  name: 'Dashboard',
  data() {
    return {
      message: 'hello',
      ecsData: null
    }
  },
  computed: {},
  mounted() {
    this.init()
  },
  beforeDestroy: function() {
  },
  methods: {
    init() {
      last5MinData().then(({ data }) => {
        this.ecsData = data
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
