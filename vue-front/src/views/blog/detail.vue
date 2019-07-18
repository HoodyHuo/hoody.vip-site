<template>
  <div class="container">
    <el-row>
      <el-col :span="24">
        <div class="title">{{ blog.title }}</div>
      </el-col>
      <el-col :span="24">
        <Markdown class="content" :content="blog.content" />
      </el-col>
      <el-col :span="24">
        <Copyright />
      </el-col>

      <el-col :span="24">
        <Comments :blogid="blog.id" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Comments from './components/Comments'
import Markdown from '@/components/Markdown'
import Copyright from '@/components/Copyright'
import { getBlog } from '@/api/blog'

export default {
  name: 'Detail',
  components: {
    Markdown,
    Comments,
    Copyright
  },
  data() {
    return {
      blog: {}
    }
  },
  created() {
    const that = this
    const id = this.$route.params.id // 获取router url路径参数 id
    getBlog(id).then(res => {
      that.blog = res.data
    })
  }
}
</script>

<style lang="scss" scoped>

.container{
    box-sizing: border-box;
    min-width: 200px;
    max-width: 980px;
    margin: 0 auto;
    padding: 45px;
}

  .title {
    box-sizing: border-box;
    min-width: 200px;
    max-width: 980px;
    margin: 0 auto;
    font-family: 幼圆;
    padding: 45px;
    font-size: 20px;
    font-weight:bolder;
    color: #d3dce6;
  }

  .content {
    background-color: rgba(245, 245, 245, 0.9);
    min-height: 500px
  }

  @media (max-width: 1440px) {
    .title {
      padding: 15px;
    }
  }
</style>
