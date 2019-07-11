/**
 * 映射store值
 * 全局可通过this.store.getters.***获取
 */
const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device
}
export default getters
