<template>
  <div class="layout-blog-container" :style="{'background-image':`url(${backgroundImage})`}">
    <el-container class="blog-container">
      <el-header class="blog-header">
        Hoody's Blog
      </el-header>
      <el-main class="blog-main">
        <el-row>
          <el-col :lg="17"><Breadcrumb class="breadcrumb" /></el-col>
          <el-col :lg="7"> <Search :text="searchText" /></el-col>
        </el-row>
        <el-row>
          <router-view />
        </el-row>
      </el-main>
      <el-footer class="blog-footer">
        <hr color="d3dce6">
        <Footer />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb'
import Footer from '@/components/Footer'
import Search from '@/components/blog/Search'

const backgroundImage = '/intro-bg.jpg'
export default {
  name: 'BlogLayout',
  components: {
    Breadcrumb,
    Footer,
    Search
  },
  data() {
    return {
      backgroundImage: backgroundImage,
      searchText: this.$route.params.query
    }
  }
}
</script>

<style lang="css" scoped>
  .layout-blog-container{
    /* 固定背景,不自动平铺  */
    background-repeat: no-repeat;
    background-attachment: fixed;
  }
  .blog-container {
    scroll-behavior: smooth; /*平滑滚动*/
    box-sizing: border-box;
    min-width: 200px;
    max-width: 1400px;
    min-height: 100%;
    margin: 0 auto;
    padding: 45px;
  }

  .breadcrumb {
    padding-left: 15px;
  }

  .blog-header {
    font-family: Impact, Charcoal, sans-serif;
    font-size: 60px;
    min-width: 400px;
    margin: auto;
    color: #ffff;
  }

  @media (min-width: 1441px) {
    .layout-blog-container {
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

    .layout-blog-container {
      padding: 15px;
    }
  }

</style>
