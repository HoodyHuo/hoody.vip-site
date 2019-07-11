<template>
  <div class="list-container">
    <div class="btn-container">
      <el-button type="primary" @click.native="newBlog">新建博客</el-button>
      <el-button type="error" @click.native="deleteBlog">删除博客</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      class="data-table"
      :data="content"
      element-loading-text="Loding ...."
      border
      fit
      highlight-current-row
      @row-click="editBlog"
      @selection-change="selectItem"
    >
      <el-table-column
        type="selection"
        show-overflow-tooltip
      />
      <el-table-column
        prop="id"
        label="ID"
        min-width="35"
      />
      <el-table-column
        prop="title"
        label="标题"
        min-width="80"
      />
      <el-table-column
        prop="content"
        label="内容"
        :formatter="subString"
        min-width="120"
      />
      <el-table-column
        prop="createTime"
        :formatter="parseTime"
        label="创建时间"
        min-width="100"
      />
      <el-table-column
        prop="modifyTime"
        :formatter="parseTime"
        label="修改时间"
        min-width="100"
      />
    </el-table>
  </div>
</template>

<script>
import { getList, deleteBlog } from '@/api/blog'
import { parseTime } from '@/utils'
import { MessageBox, Message } from 'element-ui'

export default {
  name: 'BlogList',
  components: {},
  data() {
    return {
      pageable: {
        page: 1,
        max: 10
      },
      content: [],
      totalPages: '',
      total: '',
      listLoading: true,
      selectedItem: []
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    subString(row, column, cellValue, index) {
      if (cellValue) {
        return String(cellValue).substring(0, 80)
      } else {
        return '-'
      }
    },
    editBlog(row, column, event) {
      this.$router.push('/blog/edit/' + row.id)
    },
    parseTime(ow, column, cellValue, index) {
      if (cellValue) {
        return parseTime(cellValue)
      } else {
        return '-'
      }
    },
    fetchData() {
      this.listLoading = true
      getList(this.pageable.page, this.pageable.max).then(({ data }) => {
        this.content = data.content
        this.totalPages = data.totalPages
        this.total = data.total
        this.listLoading = false
      }).catch(reason => {
        console.error(reason)
        this.listLoading = false
      })
    },
    selectItem(selection) {
      this.selectedItem = selection
    },

    deleteBlog() {
      const ids = []
      for (const key in this.selectedItem) {
        ids.push(this.selectedItem[key].id)
      }
      MessageBox.confirm('是否删除ID:' + ids.toString(), '删除确认', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBlog(ids).then((res) => {
          Message({
            message: '删除成功',
            type: 'success'
          })
          this.fetchData()
        })
      })
    },
    newBlog() {
      this.$router.push('/blog/edit/')
    }
  }

}
</script>

<style scoped>
  .data-table{
    width: 100%;
  }
  .btn-container{
    padding-left: 15px;
    padding-bottom: 15px;
    padding-top: 10px;
  }

</style>
