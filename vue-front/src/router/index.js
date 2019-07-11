import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const modulesFiles = require.context('./modules', true, /\.js$/)
const routes = []
modulesFiles.keys().reduce((res, modulePath) => {
  // const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  const value = modulesFiles(modulePath)
  routes.push(value.default)
}, {})

const route = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: routes.concat([
    {
      path: '/',
      name: '首页',
      component: () => import('@/views/index')
    },
    {
      path: '/404',
      component: () => import('@/views/404'),
      hidden: true
    },
    { path: '*', redirect: '/404', hidden: true }
  ])

})

export default route
