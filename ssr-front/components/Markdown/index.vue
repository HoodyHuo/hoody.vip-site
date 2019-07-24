<!--
Markdown 显示组件，传入content 作为prop 参数
使用 GitHub 风格
@author Hoody
-->

<template>
  <div class="markdown-body" v-html="htmlContent" />
</template>

<script>
import { getMD2HtmlClean } from '../../utils/markdown'

export default {
  name: 'Markdown',
  props: {
    'content': {
      type: String,
      default: null
    }
  },
  data() {
    return {
      cleanHtml: ''
    }
  },
  computed: {
    htmlContent: function() {
      return getMD2HtmlClean(this.$props.content)
    }
  }
  // mounted() {
  //   this.cleanHtml = getMD2HtmlClean(this.$props.content)
  // }
}
</script>

<style lang="sass">
  @import 'github-markdown-css'
</style>
