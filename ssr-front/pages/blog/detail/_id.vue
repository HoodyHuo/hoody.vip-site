<template>
  <div class="container">
    <el-row>
      <el-col :span="24">
        <div class="title">{{ blog.title }}</div>
      </el-col>
      <el-col :sm="24" :lg="20">
        <Markdown id="loading-animation" ref="md" class="content" :content="blog.content" />
      </el-col>
      <el-col :span="4">
        <div id="blog_catalog" ref="catalog" class="catalog hidden-sm-and-down" />
      </el-col>
      <el-col :sm="24" :lg="20">
        <Copyright />
      </el-col>
      <el-col id="blog-comments" :sm="24" :lg="20">
        <Comments :blogid="blog.id" @commentsChange="getCommets" />
      </el-col>
      <el-col v-for="item in commentItems" :key="item.id" class="reply-items" :sm="24" :lg="20">
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
import Catelog from '@/utils/markdown/progress-catalog'
import '@/utils/markdown/progress-catalog.css'
import 'element-ui/lib/theme-chalk/display.css'

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
  computed: {},
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
    this.createCatalog()
    window.addEventListener('scroll', this.appScroll)
    this.getCommets()
  },
  destroyed() {
    window.removeEventListener('scroll', this.appScroll)
  },
  methods: {
    createCatalog() {
      window.onScroll =
          new Catelog({
            contentEl: 'loading-animation',
            catalogEl: `blog_catalog`,
            selector: ['h1', 'h2', 'h3']
          })
    },
    appScroll(e) {
      const catelog = this.$refs.catalog
      const md = this.$refs.md.$el
      catelog.className = md.getBoundingClientRect().y >= 0 ? 'catalog hidden-sm-and-down' : 'catalog hidden-sm-and-down stacki'
    },
    refresh() {
      this.getCommets()
    },
    getCommets() {
      const that = this
      const id = this.$route.params.id // 获取router url路径参数 id
      getCommets(id).then(res => {
        that.commentItems = res.data
      })
    }
  }
}
</script>

<style scoped>

  .container {
    scroll-behavior: smooth; /*平滑滚动*/
    box-sizing: border-box;
    width: 100%;
    margin: 0 auto;
  }

  .title {
    text-shadow: 0 0 0.2em #24292e, 0 0 0.2em #55bd8c, 0 0 0.2em #e7eaeb;
    box-sizing: border-box;
    min-width: 200px;
    margin: 0 auto;
    padding: 0.1em;
    font-size: 2em;
    font-weight: bolder;
    color: #d3dce6;
  }

  .content {
    background-color: rgba(245, 245, 245, 0.9);
    padding: 45px;
  }

  @media (max-width: 1440px) {
    .title {
      padding: 15px;
    }
  }

  .catalog {
    border-radius: 30px;
    background-color: rgba(245, 245, 245, 0.9);
    font-size: 1rem;
    font-weight: bolder;
    transition: top .2s;
    width: 20em;
    /* height: 40em; */
  }

  .stacki {
    position: fixed;
    top: 0;
  }

  .reply-items {
    background-color: rgba(245, 245, 245, 0.9);
  }
</style>
