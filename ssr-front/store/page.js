// 页面基本信息store
export const state = () => ({
  breadcrumb: [{ path: '/', name: '首页' }]
})

export const mutations = {
  // 设置页面名称
  addBreadcrumb(state, breadcrumb) {
    state.breadcrumb.push(breadcrumb)
  },
  cleanBreadcrumb(state) {
    state.breadcrumb = [{ path: '/', name: '首页' }]
  }
}
