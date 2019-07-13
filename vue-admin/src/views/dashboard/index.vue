<template>
  <div class="dashboard-container">
    <el-input v-model="message" />
    <el-button type="info" @click.native="clickBtn">点击发送</el-button>

  </div>
</template>

<script>
import Stomp from 'stompjs'
import SockJS from 'sockjs-client'

export default {
  name: 'Dashboard',
  data() {
    return {
      stompClient: null,
      timer: null,
      message: 'hello'
    }
  },
  computed: {},
  mounted() {
    this.initWebSocket()
  },
  beforeDestroy: function() {
    // 页面离开时断开连接,清除定时器
    this.disconnect()
    clearInterval(this.timer)
  },
  methods: {
    clickBtn() {
      this.sendWsMessage(this.message)
    },
    sendWsMessage(msg) {
      this.stompClient.send('/app/hello', { 'X-Token': this.$store.getters.token },
        JSON.stringify({ data: msg }),
      )
    },
    onMessage(msg) {
      this.$message.success('收到消息:' + msg) // msg.body存放的是服务端发送给我们的信息
    },
    /** 初始化Socket连接 */
    initWebSocket() {
      this.connection()
      const that = this
      // 断开重连机制,尝试发送消息,捕获异常发生时重连
      this.timer = setInterval(() => {
        try {
          that.stompClient.send('test')
        } catch (err) {
          that.$message.warning('断线了: ' + err)
          that.connection()
        }
      }, 10000)
    },
    connection() {
      // 建立连接对象
      const socket = new SockJS('/api/hoody-websocket')
      // 获取STOMP子协议的客户端对象
      this.stompClient = Stomp.over(socket)
      // 定义客户端的认证信息,按需求配置
      // 向服务器发起websocket连接
      const that = this
      this.stompClient.connect({ 'X-Token': that.$store.getters.token }, () => {
        this.stompClient.subscribe('/topic/helloEcho', (msg) => { // 订阅服务端提供的某个topic
          that.onMessage(msg.body)
          msg.ack()
        })
        // 用户加入接口
      }, (err) => {
        // 连接发生错误时的处理函数
        that.$message.error(err)
      })
    }, // 连接 后台
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect()
      }
    } // 断开连接
  }
}
</script>

<style lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }

    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
</style>
