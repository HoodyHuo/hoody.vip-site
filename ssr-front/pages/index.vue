<template>
  <el-container
    :style="{'background-image':`url(${backgroundImage})`}"
    class="container"
  >
    <el-main class="content">
      <!-- <el-row > -->
      <el-col :md="{span:8,offset:8}" :xs="24">
        <el-col :span="24" class="title">Hoody's Blog</el-col>
        <el-col :span="24" class="motto">“凡事尽力而为，而后顺其自然”</el-col>
        <el-col :span="1" class="icon">
          <nuxt-link to="/blog"><svg-icon icon-class="blog" class="icon" /></nuxt-link>
        </el-col>
        <el-col :span="1" :offset="1" class="icon">
          <a href="https://github.com/HoodyHuo"><svg-icon icon-class="github" class="icon" /></a>
        </el-col>
      </el-col>
      <!-- </el-row> -->
    </el-main>
    <el-footer>
      <hr>
      <Footer />
    </el-footer>
  </el-container>
</template>

<script>
// import Logo from '@/components/Logo.vue'
import Footer from '../components/Footer'
const backgroundImage = 'https://images4.alphacoders.com/891/891151.jpg'
export default {
  name: 'FirstPage',
  components: {
    Footer
  },
  data() {
    return {
      backgroundImage
    }
  },
  head() {
    return {
      title: "Hoody's Blog"
    }
  }
}
</script>

<style>

.container{
    height: 937px;
    /* height: 100%; */
    width: 100%;
    margin: 0 auto;
    padding: 45px;
    /* 固定背景,不自动平铺  */
    background-repeat: no-repeat;
    background-attachment: fixed;
  }
.content{
  padding-top: 45px
  }
.title {
      font-family: HACKED;
      font-size: 50px;
      color: #348d9d;
      margin: 0px auto 10px auto;
      padding-top: 1px;
      text-align: left;
      font-weight: bold;
    }
     @media (max-width: 1440px) {
       .title{
       }
     }

.icon{
  color: aqua;
}

  @media (min-width: 1441px) {
    .container {
      Background-size: 100%;
    }
  }
</style>
