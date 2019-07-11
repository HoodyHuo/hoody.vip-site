<template>
  <div>
    <div class="title">{{ title }}</div>
    <Markdown class="content" :content="blogContent" />
  </div>
</template>

<script>

import Markdown from '@/components/Markdown'
import { getBlog } from '@/api/blog'

export default {
  name: 'Detail',
  components: {
    Markdown
  },
  data() {
    return {
      blogContent: '# Marked in the browser \n >OK  \n \n Rendered by **marked**.',
      title: 'Title of  Blog'
    }
  },
  created() {
    const that = this
    const id = this.$route.params.id // 获取router url路径参数 id
    getBlog(id).then(res => {
      that.blogContent = res.data.content
      that.title = res.data.title
    })
  }
}
</script>

<style lang="scss" scoped>
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
    background-color: rgba(222, 222, 222, 0.9);
    min-height: 500px
  }

  @media (max-width: 1440px) {
    .title {
      padding: 15px;
    }
  }
</style>
