
<template>
  <!-- 过渡动画 -->
  <transition name="fade">
    <div v-show="isShow" class="img-view" @click="closeView">
      <!-- 遮罩层 -->
      <div class="img-layer" />
      <div class="img">
        <img :src="currentSrc">
      </div>
    </div>
  </transition>
</template>
<script>
// use
/**
 * <BigImage ref="img" />
 * this.$refs.img.show(src)
 **/
export default {
  name: 'BigImage',
  data() {
    return {
      currentSrc: '',
      isShow: false
    }
  },
  methods: {
    show(src) {
      this.currentSrc = src
      this.isShow = true
    },
    closeView() {
      this.isShow = false
    }
  }
}
</script>
<style scoped>
  /*动画*/
  .fade-enter-active,
  .fade-leave-active {
    transition: all .2s linear;
    transform: translate3D(0, 0, 0);

  }

  .fade-enter,
  .fade-leave-active {
    transform: translate3D(100%, 0, 0);

  }

  /* bigimg */
  .img-view {
    position: inherit;
    width: 100%;
    height: 100%;

  }

  /*遮罩层样式*/
  .img-view .img-layer {
    position: fixed;
    z-index: 999;
    top: 0;
    left: 0;
    background: rgba(0, 0, 0, 0.7);
    width: 100%;
    height: 100%;
    overflow: hidden;

  }

  /*不限制图片大小，实现居中*/
  .img-view .img img {
    max-width: 100%;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: block;
    position: fixed;
    margin: auto;
    z-index: 1000;

  }
</style>
