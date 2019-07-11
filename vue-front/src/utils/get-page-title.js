/**
 * 获取页面title ，如果没有则使用默认名称
 * */
import defaultSettings from '@/settings'

const title = defaultSettings.title || '管理页面'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
