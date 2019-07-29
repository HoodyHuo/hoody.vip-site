<template>
  <div class="comments-wapper">
    <el-row>
      <el-form>
        <el-form-item>
          <el-col :span="24">
            <span class="title">添加新评论,支持Markdown格式</span>
          </el-col>
          <el-col :span="1">
            <svg-icon icon-class="markdown" :style="{width: '2em', height: '2em'}" />
          </el-col>
          <el-col :span="23">
            <el-input
              v-model="commentData.content"
              class="comments-input"
              type="textarea"
              :rows="4"
              placeholder="等待你的评论"
            />
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-col :lg="1">
            <svg-icon icon-class="name" />
          </el-col>
          <el-col :lg="{offset:0,span:11}">
            <el-input v-model="commentData.name" placeholder="称呼 *必填" />
          </el-col>
          <el-col :lg="1">
            <svg-icon icon-class="email" />
          </el-col>
          <el-col :lg="{offset:0,span:11}">
            <el-input v-model="commentData.email" placeholder="邮箱 *必填" />
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-button
            class="comments-submit"
            type="submit"
            @click.native="onSubmit"
          >提交评论
          </el-button>
        </el-form-item>
      </el-form>
    </el-row>
  </div>
</template>

<script>
/** 评论编辑器 */
import { saveComment } from '@/api/blog'
import { isEmail } from '@/api/validate'

export default {
  name: 'Comments',
  components: {},
  props: {
    // 从属博客ID
    'blogid': {
      type: Number,
      required: false,
      default: null
    },
    // 从属评论ID
    'replyto': {
      type: Number,
      required: false,
      default: null
    },
    // 追评回复的用户名称
    'replytousername': {
      type: String,
      default: null
    }
  },
  data: function() {
    return {
      commentData: {
        blogId: this.$props.blogid,
        replyTo: this.$props.replyto,
        replyToUsername: this.$props.replytousername,
        content: '',
        name: '',
        email: ''
      }
    }
  },
  watch: {
    blogid() {
      this.commentData.blogId = this.$props.blogid
    },
    replyto() {
      this.commentData.replyTo = this.$props.replyto
    },
    replytousername() {
      this.commentData.replyToUsername = this.$props.replytousername
    }
  },
  methods: {
    validateUsername(name) {
      if (name === '' || name.length > 20) {
        this.$notify.error('请填入称呼,且长度不能超过10个字符')
        return false
      } else {
        return true
      }
    },
    validateEmail(email) {
      if (email === '' || !isEmail(email)) {
        this.$notify.error('请填入合法的Email地址')
        return false
      } else {
        return true
      }
    },
    validateContent(content) {
      if (content === '') {
        this.$notify.error('请留下你的文字')
        return false
      } else {
        return true
      }
    },
    onSubmit() {
      const data = this.commentData
      if (this.validateUsername(data.name) && this.validateEmail(data.email) && this.validateContent(data.content)) {
        saveComment(data).then((res) => {
          this.$message.success('提交评论成功')
          this.$emit('commentsChange')
        })
      }
    }
  }
}
</script>

<style scoped>
  .comments-wapper {
    background-color: #2c2a2a;
    padding: 5px;
    color: #348d9d;

  }

  .svg-icon {
    margin-left: 0.5em;
    width: 1.5em;
    height: 1.5em;
  }

  .title {
    color: #348d9d;
    font-size: 1.5625rem;
    font-weight: 500;
  }

  .meta {
    margin-top: 10px;
    margin-bottom: 10px;
    text-align: center;
  }

  .comments-name {
    margin: auto;
  }

  .comments-submit {
    color: #2c2a2a;

    width: 100%;
    background-color: #348d9d;
  }
</style>
