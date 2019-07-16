import Layout from '@/layout'

export const constantRoutes = {}

export const asyncRoutes = {
  path: '/dashboard',
  component: Layout,
  redirect: '/dashboard/index',
  name: 'Dashboard',
  meta: {
    roles: ['admin'],
    title: 'Dashboard',
    icon: 'pc'
  },
  children: [
    {
      path: '/dashboard/index',
      component: () => import('@/views/dashboard/index'),
      name: 'Dashboard',
      meta: {
        breadcrumb: false,
        roles: ['admin'],
        title: 'Dashboard',
        icon: 'dashboard'
      }
    }
  ]
}
export default {
  constantRoutes,
  asyncRoutes
}

