// 页面基本信息store
export const state = () => ({
  title: '',
  breadcrumb: [{ path: '/', name: '首页' }]
})

export const mutations = {
  // 设置页面名称
  setTitle(state, title) {
    state.title = title
  },
  addBreadcrumb(state, breadcrumb) {
    state.breadcrumb.push(breadcrumb)
  },
  cleanBreadcrumb(state) {
    state.breadcrumb = [{ path: '/', name: '首页' }]
  }
}
