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
            <p class="title">{{ blog.title }}</p>
            <p>{{ cutString(blog.content) }}</p>
          </el-card>
        </nuxt-link>
      </el-timeline-item>
    </el-timeline>
    <div class="load-btn-warpper">
      <nuxt-link v-show="page>1" :to="{path:'/blog/'+(page-1)}">
        <el-button class="load-btn" type="success"> 上一页</el-button>
      </nuxt-link>
      <nuxt-link v-show="totalPagesCount>page" :to="{path:'/blog/'+(page+1)}">
        <el-button class="load-btn" type="success"> 下一页</el-button>
      </nuxt-link>
    </div>
  </div>
</template>

<script>
import { getList } from '@/api/blog'
import { parseTime } from '@/utils/time'

export default {
  name: 'Index',
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
      totalPagesCount: 2,
      needLoad: false
    }
  },
  async asyncData({ store, params, $axios }) {
    store.commit('page/addBreadcrumb', { path: `/blog`, name: '博客列表' })
    const { data } = await getList(params.page || 1, 5)
    return {
      title: "博客列表 - Hoody's Blog",
      blogs: data.content,
      page: params.page ? parseInt(params.page) : 1,
      totalPagesCount: data.totalPages
    }
  },
  methods: {
    cutString: function(str) {
      return str.substring(0, 100)
    },
    parseTime: parseTime
  }
}
</script>

<style scoped>
  a {
    text-decoration: none;
  }

  .card:hover {
    cursor: pointer;
    background-color: rgba(107, 134, 213, 0.75);
  }

  .content {
    width: 100%;
    height: 680px
  }

  .block {
    width: 1440px;
    margin: auto;
  }

  .load-btn-warpper {
    width: 100%;
    text-align: center;
  }

  .load-btn {
    margin: 0 auto;

  }

  .title {
    font-family: 幼圆;
    font-size: large;
  }

  @media (max-width: 1440px) {
    /*覆盖浏览器默认设置*/
    ul {
      padding-inline-start: 10px;
    }
     .block {
        width: 100%;
        margin: auto;
      }

    @media (max-width: 768px) {
      .block {
        height: 100%;
      }
      /* .load-btn-warpper {
      text-align: left;
      } */
    }
  }
</style>
