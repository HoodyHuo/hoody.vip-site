import Layout from '@/layout'

export const constantRoutes = {}

export const asyncRoutes = {
  path: '/dashboard',
  component: Layout,
  redirect: '/dashboard/index',
  name: '仪表盘',
  meta: {
    roles: ['admin'],
    title: '仪表盘',
    icon: 'link'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/dashboard/index'),
      name: 'dashboard',
      meta: {
        breadcrumb: true,
        roles: ['admin'],
        title: 'Dashboard'
      }
    }
  ]
}

export default {
  constantRoutes,
  asyncRoutes
}

