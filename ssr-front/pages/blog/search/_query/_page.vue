<template>
  <div class="block">
    <el-timeline class="content">
      <el-timeline-item
        v-for="blog in blogs"
        :key="blog.id"
        class="timeline"
        :timestamp="parseTime(blog.createTime)"
        placement="top"
      >
        <nuxt-link :to="'/blog/detail/'+blog.id" :alt="blog.title">
          <el-card class="card">
            <hits/>
            <p class="title" v-html=" blog.title" />
            <p v-html=" cutString(blog.content)" />
          </el-card>
        </nuxt-link>
      </el-timeline-item>
    </el-timeline>
    <div v-show="blogs.length===0" class="blog-search-none">Sorry 无相关内容</div>
    <div class="load-btn-warpper">
      <nuxt-link v-show="page>1" :to="{path:`/blog/search/${query}/${(page-1)}`}">
        <el-button class="load-btn" type="success"> 上一页</el-button>
      </nuxt-link>
      <nuxt-link v-for="index in totalPagesCount " :key="index" :to="{path:`/blog/search/${query}/${index}`}">
        <el-button :class="{ currentPage: page===index }" class="load-btn" type="success">{{ index }}</el-button>
      </nuxt-link>
      <nuxt-link v-show="totalPagesCount>page" :to="{path:`/blog/search/${query}/${(page+1)}`}">
        <el-button class="load-btn" type="success"> 下一页</el-button>
      </nuxt-link>
    </div>
  </div>
</template>

<script>
import { getList } from '~/api/blog'
import { parseTime } from '~/utils/time'

export default {
  name: 'Search',
  layout: 'blog',
  head() {
    return {
      title: this.title
    }
  },
  data() {
    return {
      blogs: [],
      pro: 'default',
      page: 1,
      query: '',
      totalPagesCount: 2,
      needLoad: false
    }
  },
  async asyncData({ store, params }) {
    console.dir(params)
    store.commit('page/addBreadcrumb', { path: `/blog`, name: `博客搜索:${params.query}` })
    const { data } = await getList(params.page || 1, 5, params.query)
    return {
      title: `${params.query}- 搜索博客 - Hoody's Blog`,
      blogs: data.content,
      page: params.page ? parseInt(params.page) : 1,
      totalPagesCount: data.totalPages,
      query: params.query
    }
  },
  methods: {
    cutString: function(str) {
      return str.substring(0, 300)
    },
    parseTime: parseTime
  }
}
</script>

<style scoped>
  @import "@/assets/css/blog/blog-page";
  >>> hits {
    color: red;
  }
</style>
