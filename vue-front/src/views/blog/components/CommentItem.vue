<template>
  <div class="container">
    <el-row>
      <el-col :span="20" class="auth">{{ comment.name }}</el-col>
      <el-col :span="1" :offset="3">{{ comment.floor }}</el-col>
    </el-row>
    <el-row>
      <el-col :span="20" class="meta">{{ comment.createTime }}</el-col>
      <el-col :span="1" :offset="2" class="reply">
        <el-button circle class="reply-icon-bg" @click.native="switchReplyEdit">
          <svg-icon icon-class="reply" class="reply-icon" />
        </el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="20" class="content">{{ comment.content }}</el-col>
    </el-row>
    <transition name="fade">
      <el-row v-show="isShowReplyText">
        <Comments ref="replyEdit" :blogid="comment.blogId" :replyto="replyTo" />
      </el-row>
    </transition>
    <el-row class="reply-list">
      <el-col v-for="item in commentReplys" :key="item.id" :offset="1" :span="23">
        <CommentItem :comment="item" :replyto="replyTo" />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import Comments from './Comments'

export default {
  name: 'CommentItem',
  components: {
    Comments
  },
  props: ['comment', 'replyto'],
  data() {
    return {
      isShowReplyText: false,
      commentReplys: this.$props.comment.replyComments
    }
  },
  computed: {
    replyTo() {
      if (this.$props.replyto) {
        return this.$props.replyto
      } else {
        return this.$props.comment.id
      }
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
  /*.reply-list{*/
    /*border-left: 0.25rem solid rgba(255,255,255,0.08);*/
    /*padding-left: 1rem;*/
    /*border-left-color: #f0f0f0;*/
  /*}*/
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
