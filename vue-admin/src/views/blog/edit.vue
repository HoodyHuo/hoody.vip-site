<template>
  <div class="container">
    <el-row :gutter="25">
      <el-input v-model="blog.title" class="title" placeholder="请输入标题" />
      <el-tag type="info">Create:{{ parseTime(blog.createTime) }}</el-tag>
      <el-tag type="warning">Modify:{{ parseTime(blog.modifyTime) }}</el-tag>
    </el-row>
    <mavon-editor
      ref="md"
      v-model="blog.content"
      class="editor"
      @save="save"
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
import { isNullObj, parseTime } from '../../utils'

/** 增加代码高亮 */
const hljs = require('highlight.js') // https://highlightjs.org/
mavonEditor.getMarkdownIt({
  highlight: function(str, lang) {
    if (lang && hljs.getLanguage(lang)) {
      try {
        return hljs.highlight(lang, str).value
      } catch (e) {
        debugger
      }
    }
    return ''
  }
})

export default {
  name: 'BlogEdit',
  components: {
    mavonEditor
  },
  state: {},
  data() {
    return {
      /** 待上传图片暂存 */
      imageList: {},
      blog: {
        content: '',
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
      if (!this.checkParams()) {
        return
      }
      this.updateImage()
      save(this.blog).then((res) => {
        this.blog.id = res.data
        this.$message.success('保存成功')
      })
    },
    /** 图片上传 */
    $imgAdd(pos, $file) {
      this.imageList[pos] = $file
    },
    $imgDel(pos) {
      delete this.imageList[pos[0]]
    },
    /** 保存前检查属性是否完整*/
    checkParams() {
      if (this.blog.title === null || this.blog.title.trim() === '') {
        this.$message.error('请输入标题!')
        return false
      }
      if (this.blog.content === null) {
        this.$message.error('正文不能是空白')
        return false
      }
      return true
    },
    updateImage() {
      if (isNullObj(this.imageList)) {
        return
      }

      var formdata = new FormData()
      for (const _img in this.imageList) {
        formdata.append('files', this.imageList[_img], _img)
      }
      addImage(formdata).then(({ data }) => {
        /**
           * 例如：返回数据为 res = [[pos, url], [pos, url]...]
           * pos 为原图片标志（0）
           * url 为上传后图片的url地址
           */
        // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)，并移除
        for (const index in data) {
          this.$refs.md.$img2Url(index, data[index])
          this.$imgDel(index)
        }
      })
    }
  }
}
</script>

<style lang="css" scoped>
  .editor {
    /*background-color: #29560d;*/
    height: 750px;
  }

  .title {
    min-width: 100px;
    max-width: 300px;
    padding: 15px;
  }

  .button {

  }

</style>
