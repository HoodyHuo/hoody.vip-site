import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

/**
 * 每次路由跳转钱进行跳转路径判断
 * 1.是否已经登录，未登录则跳转登录页
 * 2.访问无权限的路径时 跳转到404
 */
router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // 确认用户是否登录
  const hasToken = getToken()
  if (hasToken) {
    if (to.path === '/login') {
      // 如果目的地是/login，则直接前往主页
      next({ path: '/' })
      NProgress.done()
      return
    }
    // 确认用户是否已经获取权限列表
    const hasRoles = store.getters.roles && store.getters.roles.length > 0
    if (hasRoles) {
      next()
      return
    }
    try {
      // 如果没有权限，尝试获取用户权限并在此判断
      // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
      const { roles } = await store.dispatch('user/getInfo')

      // 通过roles 生成可访问路由表 generate accessible routes map based on roles
      const accessRoutes = await store.dispatch('permission/generateRoutes', roles)
      // 动态增加路由表 dynamically add accessible routes
      router.addRoutes(accessRoutes)

      // hack method to ensure that addRoutes is complete
      // set the replace: true, so the navigation will not leave a history record
      next({ ...to, replace: true })
      // next()
    } catch (error) {
      // remove token and go to login page to re-login
      await store.dispatch('user/resetToken')
      Message.error(error || '发生错误')
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  } else {
    /** has no token*/

    // 检查白名单里是否有对应地址可以访问，如果可以访问则前往
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
      return
    }
    // other pages that do not have permission to access are redirected to the login page.
    next(`/login?redirect=${to.path}`)
    NProgress.done()
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
