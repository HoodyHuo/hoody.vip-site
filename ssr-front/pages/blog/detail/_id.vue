<template>
  <div class="container">
    <el-row>
      <el-col :span="24">
        <div class="title">{{ blog.title }}</div>
      </el-col>
      <el-col :span="24">
        <Markdown class="content" :content="blog.content" />
      </el-col>
      <el-backtop target=""></el-backtop>
      <el-col :span="24">
        <Copyright />
      </el-col>

      <el-col :span="24">
        <Comments :blogid="blog.id" @commentsChange="getCommets" />
      </el-col>
    </el-row>
    <el-row class="reply-items">
      <el-col v-for="item in commentItems" :key="item.id" :span="24">
        <CommentItem :comment="item" @commentsReply="refresh" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Comments from '@/components/blog/Comments'
import CommentItem from '@/components/blog/CommentItem'
import Markdown from '@/components/Markdown'
import Copyright from '@/components/Copyright'
import { getBlog, getCommets } from '@/api/blog'

export default {
  // layout: 'blog',
  name: 'Detail',
  components: {
    Markdown,
    Comments,
    Copyright,
    CommentItem
  },
  head() {
    return {
      title: `Hoody's Blog - 个人博客 - ${this.blog.title}`,
      meta: [
        { name: 'tag', content: this.blog.tag || '' }
      ]
    }
  },
  data() {
    return {
      commentItems: []
    }
  },
  computed: {
  },
  asyncData({ store, params, redirect, $axios }) {
    store.commit('page/addBreadcrumb', { path: `/blog/detail/${params.id}`, name: '博客详情' })
    return getBlog(params.id)
      .then(({ data }) => {
        return { blog: data }
      }).catch((e) => {
        redirect('/404')
      })
  },
  mounted() {
    this.getCommets()
  },
  methods: {
    refresh() {
      this.getCommets()
    },
    getCommets() {
      const that = this
      const id = this.$route.params.id // 获取router url路径参数 id
      getCommets(id).then(res => {
        that.commentItems = res.data
      })
    },
    getBlog() {
      const that = this
      const id = this.$route.params.id // 获取router url路径参数 id
      getBlog(id).then(res => {
        that.blog = res.data
      })
    }
  }
}
</script>

<style scoped>

  .container {
    scroll-behavior: smooth; /*平滑滚动*/
    box-sizing: border-box;
    min-width: 200px;
    max-width: 980px;
    margin: 0 auto;
  }

  .title {
    box-sizing: border-box;
    min-width: 200px;
    max-width: 980px;
    margin: 0 auto;
    font-family: 幼圆;
    padding: 45px;
    font-size: 20px;
    font-weight: bolder;
    color: #d3dce6;
  }

  .content {
    background-color: rgba(245, 245, 245, 0.9);
    min-height: 500px;
    padding: 45px;
  }

  @media (max-width: 1440px) {
    .title {
      padding: 15px;
    }
  }

  .reply-items {
    background-color: rgba(245, 245, 245, 0.9);
  }
</style>
