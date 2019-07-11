<!--
Markdown 显示组件，传入content 作为prop 参数
使用 GitHub 风格
@author Hoody
-->

<template>
  <div class="markdown-body" v-html="htmlContent" />

</template>

<script>
const marked = require('marked')
import 'github-markdown-css'

export default {
  name: 'Markdown',
  props: ['content'],
  data() {
    return {
      htmlContent: marked(this.$props.content)
    }
  },
  watch: {
    content(newVal, oldVal) {
      this.htmlContent = marked(newVal)
    }
  }
}
</script>

<style scoped>
  .markdown-body {
    box-sizing: border-box;
    min-width: 200px;
    max-width: 980px;
    margin: 0 auto;
    padding: 45px;
  }

  @media (max-width: 767px) {
    .markdown-body {
      padding: 15px;
    }
  }
</style>
