<!--
Markdown 显示组件，传入content 作为prop 参数
使用 GitHub 风格
@author Hoody
-->

<template>
  <div>
    <div ref="md" class="markdown-body" v-html="htmlContent" />
    <BigImage ref="img" />
  </div>
</template>

<script>
import { getMD2HtmlClean } from '../../utils/markdown'
import BigImage from '../BigImage/'

export default {
  name: 'Markdown',
  components: {
    BigImage
  },
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
  },
  mounted() {
    const _this = this
    this.$refs.md.addEventListener('click', function(event) {
      if (event.srcElement.tagName === 'IMG') {
        _this.$refs.img.show(event.srcElement.src)
      }
    })
  },
  methods: {
    viewImg(src) {
      this.showImg = true
      this.imgSrc = src
    },
    closeViewImg() {
      this.showImg = false
    }
  }

}
</script>

<style lang="sass">
  @import 'github-markdown-css'
</style>
