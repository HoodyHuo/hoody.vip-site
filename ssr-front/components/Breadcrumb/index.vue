<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item) in levelList" :key="item.path">
        <nuxt-link class="link-breadcrumb" :to="item.path">{{ item.name }}</nuxt-link>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import pathToRegexp from 'path-to-regexp'

export default {
  data() {
    return {
      levelList: null
    }
  },
  watch: {
    $route() {
      this.getBreadcrumb()
    }
  },
  created() {
    this.getBreadcrumb()
  },
  methods: {
    /** 从VUEX中获取
     * page 组件 在AsyncData方法提交
     *  store.commit('page/addBreadcrumb', { path: `/blog/detail/${params.id}`, name: '博客详情' })*/
    getBreadcrumb() {
      this.levelList = this.$store.state.page.breadcrumb
    },

    pathCompile(path) {
      // To solve this problem https://github.com/PanJiaChen/vue-element-admin/issues/561
      const { params } = this.$route
      var toPath = pathToRegexp.compile(path)
      return toPath(params)
    },
    handleLink(item) {
      const { path } = item
      this.$router.push(path)
    }
  }
}
</script>

<style lang="scss" scoped>
  .link-breadcrumb{
    color: #d3dce6;
    font-size: 20px;
  }

  .app-breadcrumb.el-breadcrumb {
    display: inline-block;
    font-size: 14px;
    line-height: 50px;
    margin-left: 8px;

    .no-redirect {
      color: #8c9db4;
      cursor: text;
      font-size: 20px;
    }
  }
</style>
