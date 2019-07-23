<template>
  <div class="block">
    <el-timeline>
      <el-timeline-item
        v-for="blog in blogs"
        :key="blog.id"
        class="timeline"
        :timestamp="parseTime(blog.createTime)"
        placement="top"
      >
        <el-card class="card" herf="detail/" @click.native="toDetail(blog.id)">
          <p class="title">{{ blog.title }}</p>
          <p>{{ cutString(blog.content) }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <div class="load-btn-warpper">
      <el-button class="load-btn" type="success" @click.native="onLoad"> 点击 加载更多</el-button>
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
      title: '近期博客 Hoody'
    }
  },
  data() {
    return {
      blogs: [],
      page: 1,
      totalPagesCount: 2,
      needLoad: false
    }
  },
  created() {
    this.getBlogs(this.page)
  },
  methods: {
    onLoad() {
      if (this.page > this.totalPagesCount) {
        this.$notify.warning({ title: '通知', message: '没有更多内容了' })
      } else {
        this.getBlogs(this.page)
      }
    },
    getBlogs(page) {
      getList(page, 5).then(res => {
        this.blogs = this.blogs.concat(res.data.content)
        this.totalPagesCount = res.data.totalPages
        this.page += 1
      })
    },
    cutString: function(str) {
      return str.substring(0, 100)
    },
    parseTime: parseTime,
    toDetail: function(id) {
      this.$router.push('/blog/detail/' + id)
    }
  }
}
</script>

<style scoped>
  .card:hover {
    cursor: pointer;
    background-color: rgba(107, 134, 213, 0.75);
  }

  .block {
    width: 1400px;
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

  @media (max-width: 1439px) {
    /*覆盖浏览器默认设置*/
    ul {
      padding-inline-start: 10px;
    }

    .load-btn-warpper {
      text-align: left;
    }
  }
</style>
