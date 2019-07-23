<template>
  <el-container
    :style="{'background-image':`url(${backgroundImage})`}"
    class="container"
  >
    <el-main>
      <el-col :md="{span:10,offset:8}" :xs="24" class="">
        <el-row class="content">
          <el-col :span="24" class="title">Hoody's Blog</el-col>
          <el-col :span="24" class="motto">“凡事尽力而为，而后顺其自然”</el-col>
          <el-col :span="23" :offset="1" class="icon">
            <nuxt-link to="/blog"><svg-icon icon-class="blog" class="icon" /></nuxt-link>
            <a href="https://github.com/HoodyHuo">
              <svg-icon icon-class="github" class="icon" />
            </a>
          </el-col>
        </el-row>
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
const backgroundImage = 'intro-bg.jpg'
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

<style scoped>
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
    margin-top: 20%;
    padding-bottom: 30px;
    background-color: rgb(209, 216, 221);
    border-radius: 25px;
  }
.title {
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
  padding-top:1em;
}
  .svg-icon {
    color: #348d9d;
    height: 6em;
    width: 6em;
  }
.motto{
 font-size: 50px;
      color: #348d9d;
}

  /* @media (min-width: 1441px) {
    .container {
      Background-size: 100%;
    }
  } */
</style>
