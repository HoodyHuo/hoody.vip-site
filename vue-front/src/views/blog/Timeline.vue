<template>
  <div class="block">
    <el-timeline>
      <el-timeline-item v-for="blog in blogs" :key="blog.id" class="timeline" :timestamp="parseTime(blog.createTime)" placement="top">
        <el-card class="card" @click.native="toDetail(blog.id)">
          <p class="title">{{ blog.title }}</p>
          <p>{{ cutString(blog.content) }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
import { timeline } from '@/api/blog'
import { parseTime } from '../../utils'

export default {
  name: 'Index',
  data() {
    return {
      blogs: ''
    }
  },
  created() {
    timeline().then(res => {
      this.blogs = res.data
    })
  },
  methods: {
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
  .card:hover{
    background-color: rgba(107, 134, 213, 0.75);
  }
  .block{
    width: 1400px;
    margin: auto;
  }
.title{
  font-family: 幼圆;
  font-size: large;
}
  @media (max-width: 1439px) {
    /*覆盖浏览器默认设置*/
    ul{
      padding-inline-start: 10px;
    }
  }
</style>
