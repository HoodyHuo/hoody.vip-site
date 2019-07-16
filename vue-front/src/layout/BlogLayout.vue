<template>
  <el-container
    class="blog-container"
    :style="{'background-image':`url(${backgroundImage})`}"
  >
    <el-header class="blog-header">
      Hoody's Blog
    </el-header>
    <el-main class="blog-main">
      <Breadcrumb class="breadcrumb" />
      <router-view />
    </el-main>
    <el-footer class="blog-footer">
      <hr color="d3dce6">
      <Footer />
    </el-footer>

  </el-container>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb'
import Footer from './components/Footer'

const backgroundImage = 'https://images4.alphacoders.com/891/891151.jpg'
export default {
  name: 'BlogLayout',
  components: {
    Breadcrumb,
    Footer
  },
  data() {
    return {
      backgroundImage: backgroundImage
    }
  }
}
</script>

<style lang="css" scoped>
  .blog-container {
    box-sizing: border-box;
    min-width: 300px;
    /*max-width: 100%;*/
    min-height: 100%;
    margin: 0 auto;
    padding: 45px;
    /* 固定背景,不自动平铺  */
    background-repeat: no-repeat;
    background-attachment: fixed;
  }

  .breadcrumb {
    padding-left: 100px;
  }

  .blog-header {
    font-family: Impact, Charcoal, sans-serif;
    font-size: 60px;
    min-width: 400px;
    margin: auto;
    color: #ffff;
  }

  .blog-footer {
  }

  .blog-main {
  }

  @media (min-width: 1441px) {
    .blog-container {
      Background-size: 100%;
    }
  }

  @media (max-width: 1440px) {
    .blog-main {
      padding: 5px;
    }

    .breadcrumb {
      padding-left: 15px;
    }

    .blog-container {
      padding: 15px;
    }
  }

</style>
