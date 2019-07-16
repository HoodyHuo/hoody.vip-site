<template>
  <div class="banner">
    <div class="banner-container">
      <div class="title-container">
        <h3 class="title">Hoody's</h3>
        <h3 class="title">Personal Blog</h3>
      </div>
      <div class="btn-box">
        <el-button type="success" round @click="gotoBlog">Entry Blog</el-button>
        <el-button type="primary" round @click="gotoAdmin">Dangerous</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { Button } from 'element-ui'

// const backgroundImage = '/intro-bg.jpg'
export default {
  name: 'IntroBanner',
  components: {
    [Button.name]: Button
  },
  data() {
    return {
      adminSrc: process.env.VUE_APP_ADMIN_URL
    }
  },
  methods: {
    gotoBlog() {
      this.$router.push('/blog')
    },
    gotoAdmin() {
      window.location.href = this.adminSrc
    }
  }
}
</script>

<style lang="scss">

  .banner{
    background-repeat:no-repeat;
    min-height: 100%;
    width: 100%;
    overflow: hidden;
  }
  .banner-container{
    position: relative;
    width: 600px;
    max-width: 100%;
    padding-top: 200px ;
    margin: 0 auto;
    overflow: hidden;
  }
   .title-container {
    position: relative;

    .title {
      font-family: HACKED;
      font-size: 50px;
      color: #1a4049;
      margin: 0px auto 10px auto;
      padding-top: 1px;
      text-align: left;
      font-weight: bold;
    }
     @media (max-width: 1500px) {
       .title{
         color: #9cb8aa;
       }
     }
  }
  .btn-box{
    padding-top: 40px;
    position: relative;
    .el-button--primary {
      color: #FFF;
      background-color: #305f73;
      border-color: #37585d;
    }
    .el-button--success {
      color: #FFF;
      background-color: #6c7f7b;
      border-color: #4d6768;
    }
  }

</style>
