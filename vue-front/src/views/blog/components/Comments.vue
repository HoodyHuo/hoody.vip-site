<template>
  <div class="comments-wapper">
    <el-row>
      <el-form>
        <el-form-item><span class="title">添加新评论,支持Markdown格式</span>
        </el-form-item>
        <el-input
          v-model="commentData.content"
          class="comments-input"
          type="textarea"
          :rows="4"
          placeholder="等待你的评论"
        />
        <el-form-item :inline="true" class="meta">
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
        <el-button
          class="comments-submit"
          type="submit"
          @click.native="onSubmit"
        >提交评论
        </el-button>
      </el-form>
    </el-row>
    <el-row>
      <el-col class="comments-totla" :span="24">总计1条</el-col>
    </el-row>
  </div>
</template>

<script>
import { saveComment } from '@/api/blog'

export default {
  name: 'Comments',
  components: {},
  props: ['blogid', 'replyto'],
  data: function() {
    return {
      commentData: {
        blogId: this.$props.blogid,
        replyTo: this.$props.replyto,
        content: '',
        name: '',
        email: ''
      }
    }
  },
  watch: {
    blogid() {
      this.commentData.blogid = this.$props.blogid
    },
    replyto() {
      this.commentData.replyTo = this.$props.replyto
    }
  },
  methods: {
    validateUsername(name) {
      if (name === '' || name.length > 20) {
        this.$notify.error('必须填入称呼,且长度不能超过10个字符')
        return false
      } else {
        return true
      }
    },
    validateEmail(email) {
      if (email === '' || email.length > 20) {
        this.$notify.error('必须填入称呼,且长度不能超过10个字符')
        return false
      } else {
        return true
      }
    },
    validateContent(content) {
      if (content === '') {
        this.$notify.error('评论内容不能是空白')
        return false
      } else {
        return true
      }
    },
    onSubmit() {
      const data = this.commentData
      if (this.validateUsername(data.name) && this.validateEmail(data.email) && this.validateContent(data.content)) {
        saveComment(data).then((res) => {
          if (res.success) {
            this.$emit('commentsChange')
          } else {
            this.$notify.warning(JSON.stringify(res.msg))
          }
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
    color: #1abc9c;
  }

  .title {
    color: #1abc9c;
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
    background-color: #1abc9c;
  }
</style>
