<template>
  <el-form class="comments-wapper">
    <el-form-item><span class="title">添加新评论</span></el-form-item>

    <el-input
      v-model="comment.content"
      class="comments-input"
      type="textarea"
      :rows="4"
      placeholder="等待你的评论"
    />
    <el-form-item :inline="true" :gutter="40">
      <el-col :lg="{offset:0,span:12}">
        <el-input v-model="comment.name" placeholder="称呼" />
      </el-col>
      <el-col :lg="{offset:0,span:12}">
        <el-input v-model="comment.email" placeholder=" 邮箱" />
      </el-col>
    </el-form-item>
    <el-button
      class="comments-submit"
      type="submit"
    >提交评论
    </el-button>
  </el-form>

</template>

<script>
export default {
  name: 'Comments',
  components: {},
  props: ['blogid'],
  data: function() {
    return {
      comment: {
        content: '',
        name: '',
        email: ''
      }
    }
  },
  watch: {
    blogid() {
      this.textarea = this.$props.blogid
    }
  }
}
</script>

<style scoped>
.comments-wapper{
    background-color: #2c2a2a;
      padding: 5px;
}
.title{
    color: #1abc9c;
    font-size: 1.5625rem;
    font-weight: 500;
}
.comments-name{
  margin: auto;
}
.comments-submit{
  width: 100%
}
</style>
