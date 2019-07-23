<template>
  <nuxt-child />
</template>

<script>

export default {
  layout: 'blog',
  // todo 通过嵌套路由向 state放数据 面包学通过state 生成
  asyncData({ store }) {
    store.commit('page/addBreadcrumb', { path: '/blog', name: '博客列表' })
  }
}
</script>

<style>

</style>
