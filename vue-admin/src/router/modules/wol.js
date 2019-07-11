import Layout from '@/layout'

export const constantRoutes = {}

export const asyncRoutes = {
  path: '/wol',
  component: Layout,
  redirect: '/wol/index',
  name: 'WOL',
  meta: {
    roles: ['admin'],
    title: 'WOL',
    icon: 'pc'
  },
  children: [
    {
      path: '/wol/index',
      component: () => import('@/views/wol/index'),
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

