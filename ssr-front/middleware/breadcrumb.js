/**
 * 面包屑中间件,在路由跳转时,清除
 * @param {*} param0
 */
export default function({ store }) {
  store.commit('page/cleanBreadcrumb')
}
