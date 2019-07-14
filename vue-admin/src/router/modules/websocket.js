import Layout from '@/layout'

export const constantRoutes = {}

export const asyncRoutes = {
  path: '/websockekt',
  component: Layout,
  redirect: '/websockekt/index',
  name: 'Websockekt',
  meta: {
    roles: ['admin'],
    title: 'websockekt',
    icon: 'pc'
  },
  children: [
    {
      path: '/websockekt/index',
      component: () => import('@/views/websocket/index'),
      name: 'WOL',
      meta: {
        breadcrumb: false,
        roles: ['admin'],
        title: 'WOL',
        icon: 'link'
      }
    }
  ]
}
export default {
  constantRoutes,
  asyncRoutes
}

