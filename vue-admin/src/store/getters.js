/**
 * 映射store值
 * 全局可通过this.store.getters.***获取
 */
const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  roles: state => state.user.roles,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  routes: state => state.permission.routes
}
export default getters
