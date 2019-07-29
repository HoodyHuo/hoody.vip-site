<template>
  <div class="layout-blog-container" :style="{'background-image':`url(${backgroundImage})`}">
    <div class="blog-container">
      <el-row id="blog_header" class="blog-header">
        Hoody's Blog
      </el-row>
      <el-row class="blog-main">
        <el-row>
          <el-col :lg="17" :xs="24"><Breadcrumb class="breadcrumb" /></el-col>
          <el-col :lg="7" :xs="24"> <Search :text="searchText" /></el-col>
        </el-row>
        <el-row>
          <el-col span="24"><router-view /></el-col>
        </el-row>
      </el-row>
      <el-row>
        <el-col span="24"><hr color="d3dce6"></el-col>
        <el-col span="24"> <Footer /></el-col>
      </el-row>
    </div>
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

<style scoped>
  .layout-blog-container{
    /* 固定背景,不自动平铺  */
    background-repeat: no-repeat;
    background-attachment: fixed;
  }
  .blog-container {
    scroll-behavior: smooth; /*平滑滚动*/
    box-sizing: border-box;
    min-width: 200px;
    max-width: 1440px;
    /*min-height: 100%;*/
    margin: 0 auto;
    padding: 45px;
  }
  .blog-main{
    max-width: 100%;
    height: auto;
  }

  .breadcrumb {
    padding-left: 15px;
  }

  .blog-header {
    font-family: Impact, Charcoal, sans-serif;
    font-size: 4em;
    margin: auto;
    color: #ffff;
  }

  @media (min-width: 1441px) {
    .layout-blog-container {
      Background-size: 100%;
    }
  }

  @media (max-width: 1440px) {
    .blog-container {
      padding: 5px;
      width: 100%;
    }

    .breadcrumb {
      padding-left: 15px;
    }

    .layout-blog-container {
      padding: 15px;
    }
  }

</style>
