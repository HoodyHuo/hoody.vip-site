<template>
  <el-container :style="{'background-image':`url(${backgroundImage})`}" class="container">
    <el-main class="intro">
      <IntroBanner />
    </el-main>
    <el-footer>
      <hr>
      <Footer class="footer" />
    </el-footer>
  </el-container>
</template>

<script>
import IntroBanner from './components/banner'
import Footer from '@/layout/components/Footer'
const backgroundImage = 'https://images4.alphacoders.com/891/891151.jpg'

//TODO 做首页兼容
export default {
  name: 'Intro',
  components: {
    IntroBanner,
    Footer
  },
  data() {
    return {
      backgroundImage: backgroundImage
    }
  }
}
</script>

<style scoped>
  .container{
    min-height: 100%;
    margin: 0 auto;
    padding: 45px;
    /* 固定背景,不自动平铺  */
    background-repeat: no-repeat;
    background-attachment: fixed;
  }

  @media (min-width: 1441px) {
    .container {
      Background-size: 100%;
    }
  }
</style>
