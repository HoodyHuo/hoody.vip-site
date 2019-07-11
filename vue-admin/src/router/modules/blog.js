import Layout from '@/layout'

export const constantRoutes = {}

export const asyncRoutes = {
  path: '/blog',
  component: Layout,
  redirect: '/blog/list',
  name: '博客',
  meta: {
    roles: ['admin'],
    title: '博客',
    icon: 'link'
  },
  children: [
    {
      path: 'list',
      component: () => import('@/views/blog/list'),
      name: 'Blog list',
      meta: {
        breadcrumb: true,
        roles: ['admin'],
        title: '博客列表',
        icon: 'link'
      }
    },
    {
      path: 'edit/:id',
      component: () => import('@/views/blog/edit'),
      name: 'Blog edit',
      hidden: true,
      meta: {
        breadcrumb: true,
        roles: ['admin'],
        title: '博客编辑',
        icon: 'link'
      }
    },
    {
      path: 'edit',
      component: () => import('@/views/blog/edit'),
      name: 'new Blog',
      hidden: true,
      meta: {
        breadcrumb: true,
        roles: ['admin'],
        title: '新建博客',
        icon: 'link'

      }
    }
  ]
}

export default {
  constantRoutes,
  asyncRoutes
}

