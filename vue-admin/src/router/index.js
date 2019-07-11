import Vue from 'vue'
import Router from 'vue-router'
import { isNullObj } from '@/utils'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/** 获取src/router/modules/目录下的所有路由文件 */
const modulesConstantRoutes = []
const modulesAsyncRoutes = []
const modulesFiles = require.context('./modules', true, /\.js$/)
modulesFiles.keys().reduce((res, modulePath) => {
  const value = modulesFiles(modulePath)
  const { constantRoutes, asyncRoutes } = value
  if (isNullObj(constantRoutes) === false) {
    modulesConstantRoutes.push(constantRoutes)
  }
  if (isNullObj(asyncRoutes) === false) {
    modulesAsyncRoutes.push(asyncRoutes)
  }
}, {})

/**
 * Note: 子菜单只会在 route children.length >= 1 时添加
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, 选项不会再菜单中出现 (default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },

  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/form/index'),
        meta: { title: 'Form', icon: 'form' }
      }
    ]
    // modules.constantRoutes,
  }].concat(modulesConstantRoutes).concat([
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
])

export const asyncRoutes = [
  {
    path: '/example',
    component: Layout,
    redirect: '/example/table',
    name: 'Example',
    meta: { title: 'Example', icon: 'example', roles: ['1111', 'editor'] },
    children: [
      {
        path: 'table',
        name: 'Table',

        component: () => import('@/views/table/index'),
        meta: { title: 'Table', icon: 'table', roles: ['1111', 'editor'] }
      },
      {
        path: 'tree',
        name: 'Tree',

        component: () => import('@/views/tree/index'),
        meta: { title: 'Tree', icon: 'tree', roles: ['editor'] }
      }
    ]
  }
].concat(modulesAsyncRoutes) /** modules routes */

const createRouter = () => new Router({
  base: process.env.VUE_APP_BASE, // require service support

  mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
