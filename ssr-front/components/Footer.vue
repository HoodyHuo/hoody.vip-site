<template>
  <div class="footer-container">
    <p>© 2019-2020 Hoody | 备案号 <a :href="icp" target="_blank" >蜀ICP备19021783号</a></p>
  </div>
</template>

<script>
export default {
  name: 'Footer',
  data() {
    return {
      icp: 'https://beian.miit.gov.cn'
    }
  }
}
</script>

<style scoped>
.footer-container {
  margin: auto;
  float: right;
  color: #d3dce6;
  box-sizing: border-box;
}
</style>
