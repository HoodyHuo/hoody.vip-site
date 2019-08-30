<template>
  <el-row class="container">
    <el-col :span="19" :offset="1" class="item">
      <el-input v-model="input" placeholder="你要搜索什么?" clearable prefix-icon="el-icon-search" @keypress.enter.native="searchBlog" />
    </el-col>
    <el-col :span="4" class="item">
      <el-button icon="el-icon-search" circle @click.native="searchBlog" />
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'Search',
  props: {
    'text': {
      type: String,
      required: true,
      default: ''
    }
  },
  data() {
    return {
      input: ''
    }
  },
  mounted() {
    this.input = this.$props.text
  },
  methods: {
    searchBlog() {
      if (this.input !== '') {
        this.$router.push(`/blog/search/${this.input}`)
      }
    }
  }
}
</script>

<style scoped>
.container{
  padding-top: 1em;
  padding-bottom: 1em;

}
</style>
