<template>
  <div class="container">
    <!--头部-->
    <el-row>
      <el-col :span="23">
        <el-row>
          <el-col :span="1">
            <svg-icon icon-class="person" />
          </el-col>
          <el-col :span="20" class="auth">{{ comment.name }}</el-col>
          <el-col v-if="!isReply" :span="2" :offset="1">#{{ comment.floor }}</el-col>
        </el-row>
        <!--meta 信息-->
        <el-row>
          <el-col :span="20" :offset="1" class="meta">{{ formatCreateTime }}</el-col>
          <el-col v-show="comment.replyToUsername" :span="20" class="meta">回复:@{{ comment.replyToUsername }}</el-col>
        </el-row>
      </el-col>
      <el-col :span="1" class="reply">
        <el-button circle class="reply-icon-bg" @click.native="switchReplyEdit">
          <svg-icon icon-class="reply" class="reply-icon" />
        </el-button>
      </el-col>
    </el-row>
    <!--文字-->
    <el-row>
      <el-col :span="24">
        <Markdown :content="commentText" class="content" />
      </el-col>
    </el-row>
    <!--编辑-->
    <transition name="fade">
      <el-row v-show="isShowReplyText">
        <Comments
          ref="replyEdit"
          :blogid="comment.blogId"
          :replyto="replyTo"
          :replytousername="comment.name"
          @commentsChange="refreshComments"
        />
      </el-row>
    </transition>
    <!--追评-->
    <el-row class="reply-list">
      <el-col v-for="item in commentReplys" :key="item.id" :offset="1" :span="23">
        <CommentItem class="reply-item" :comment="item" :replyto="replyTo" @refreshComments="refreshComments" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Comments from './Comments'
import { formatTime } from '../../utils/time'
import { getMD2HtmlClean } from '../../utils/markdown'
import Markdown from '../Markdown'
import { getCommetsReply } from '@/api/blog'

export default {
  name: 'CommentItem',
  components: {
    Markdown,
    Comments
  },
  props: {
    // 评论对象
    'comment': {
      type: Object,
      default: null
    },
    // 如果存在,则表明追评属于哪个评论
    'replyto': {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      isShowReplyText: false, // 是否显示编辑器
      commentReplys: this.$props.comment.replyComments, // 追评列表
      commentText: '' // 评论正文
    }
  },
  computed: {
    /** 格式化评论时间 */
    formatCreateTime() {
      return formatTime(new Date(this.$props.comment.createTime))
    },
    /** 记录回复的评论ID,如果是对追评回复,则记录追评所属的评论ID, */
    replyTo() {
      if (this.$props.replyto) {
        return this.$props.replyto
      } else {
        return this.$props.comment.id
      }
    },
    /** 当前评论是否是追评 */
    isReply() {
      return this.$props.comment.replyTo !== null
    }
  },
  mounted() {
    /** 在加载md文本前,进行html转换 */
    this.commentText = getMD2HtmlClean(this.$props.comment.content)
  },
  methods: {
    /** 回复按钮点击,打开编辑器,并滚动至 */
    switchReplyEdit() {
      this.isShowReplyText = !this.isShowReplyText
      this.$nextTick().then(() => {
        this.$refs.replyEdit.$el.scrollIntoView({ behavior: 'smooth', block: 'nearest' })
      })
    },
    /** 当提交追评时,具备刷新追评信息 */
    refreshComments() {
      this.isShowReplyText = false
      /** 如果当前是追评,则通知上层进行刷新 */
      if (this.isReply) {
        this.$emit('refreshComments')
      } else {
        /** 否则,刷新自己名下的追评 */
        getCommetsReply(this.replyTo).then(({ data }) => {
          this.commentReplys = data
        })
      }
    }
  }
}
</script>

<style scoped>
  .container {
    padding: 15px;
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

  .reply-item {
    box-sizing: border-box;
    min-width: 200px;
    max-width: 980px;
    margin: 0 auto;
    padding: 5px;
  }

  .content {
    background-color: #ced1d3;
    min-width: 200px;
    max-width: 980px;
    margin: 0 auto;
    padding: 15px;
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
