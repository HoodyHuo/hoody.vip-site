<template>
  <div class="container">
    <el-input v-model="blog.title" />
    <input v-model="blog.title" type="text"><span>创建时间：{{ parseTime(blog.createTime) }}</span>
    <mavon-editor
      ref="md"
      v-model="blog.content"
      class="editor"
      @imgAdd="$imgAdd"
      @imgDel="$imgDel"
    />
    <el-button class="button" type="primary" @click="save">保存</el-button>
  </div>
</template>

<script>
import { addImage, save, getBlog } from '../../api/blog'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import { Message } from 'element-ui'
import { isNullObj, parseTime } from '../../utils'

/** 增加代码高亮 */
const hljs = require('highlight.js') // https://highlightjs.org/
mavonEditor.getMarkdownIt({
  highlight: function(str, lang) {
    if (lang && hljs.getLanguage(lang)) {
      try {
        return hljs.highlight(lang, str).value
      } catch (e) { debugger }
    }
    return ''
  }
})

export default {
  name: 'BlogEdit',
  components: {
    mavonEditor
  },
  state: {

  },
  data() {
    return {
      /** 待上传图片暂存 */
      imageList: {},
      blog: {
        content: null,
        title: null,
        id: null,
        createTime: null,
        modifyTime: null
      }
    }
  },
  created() {
    const that = this

    const id = this.$route.params.id || null// 获取router url路径参数id
    if (id === null) {
      return
    }
    getBlog(id).then(res => {
      that.blog = res.data
    })
  },
  methods: {
    parseTime: parseTime,
    save: function() {
      this.updateImage()
      save(this.blog).then((res) => {
        this.blog.id = res.data
        Message({
          message: '保存成功',
          type: 'success',
          duration: 2 * 1000
        })
      })
    },
    /** 图片上传 */
    $imgAdd(pos, $file) {
      this.imageList[pos] = $file
    },
    $imgDel(pos) {
      delete this.imageList[pos[0]]
    },
    updateImage() {
      if (isNullObj(this.imageList)) {
        return
      }

      var formdata = new FormData()
      for (const _img in this.imageList) {
        formdata.append(_img, this.imageList[_img])
      }
      addImage(formdata).then((res) => {
        /**
         * 例如：返回数据为 res = [[pos, url], [pos, url]...]
         * pos 为原图片标志（0）
         * url 为上传后图片的url地址
         */
        // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)，并移除
        for (const img in res) {
          this.$refs.md.$img2Url(img[0], img[1])
          this.$imgDel(img[0])
        }
      })
    }
  }
}
</script>

<style lang="css" scoped>
  .editor{
    /*background-color: #29560d;*/
    height:750px;
  }
  .button{

  }

</style>
