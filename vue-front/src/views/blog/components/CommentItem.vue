<template>
  <div class="container">
    <el-row>
      <el-col :span="20" class="auth">Hoody</el-col>
    </el-row>
    <el-row>
      <el-col :span="20" class="meta">一天前</el-col>
      <el-col :span="1" :offset="2" class="reply">
        <el-button circle class="reply-icon-bg" @click.native="switchReplyEdit">
          <svg-icon icon-class="reply" class="reply-icon" />
        </el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20" class="content">这就是评论正文,对于xkxkxkx</el-col>
    </el-row>
    <el-row class="reply-list">
      <el-col v-for="item in commentReplys" :key="item.id" :span="24">
        <CommentItem />
      </el-col>
    </el-row>
    <transition name="fade">
      <el-row v-show="isShowReplyText">
        <Comments ref="replyEdit" />
      </el-row>
    </transition>
  </div>
</template>
<script>
import Comments from './Comments'

export default {
  name: 'CommentItem',
  components: {
    Comments
  },
  props: ['blogid'],
  data() {
    return {
      isShowReplyText: false,
      commentReplys: []
    }
  },
  methods: {
    switchReplyEdit() {
      this.isShowReplyText = !this.isShowReplyText
      this.$nextTick().then(() => {
        this.$refs.replyEdit.$el.scrollIntoView()
      })
    }
  }
}
</script>

<style scoped>
  .container {
    padding-top: 15px;
  }

  .auth {
    font-size: 20px;
    font-weight: 500;
  }

  .reply-icon {
    fill: #2c2a2a;
  }

  .reply-icon-bg {
    background-color: #1abc9c;
  }

  .reply {
    cursor: pointer;
    color: #55869a;
    font-size: 20px;
    font-weight: 500;
  }

  .meta {
    color: #8e8e8e;
    font-size: 15px;
    font-weight: 500;
    margin-bottom: 10px
  }
</style>
