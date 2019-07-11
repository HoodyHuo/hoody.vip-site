import BlogLayout from '@/layout/BlogLayout'

export default {
  path: '/blog',
  component: BlogLayout,
  redirect: '/blog/timeline',
  name: '个人博客',
  meta: {
    title: '博客'
  },
  children: [
    {
      path: '/blog/timeline',
      component: () => import('@/views/blog/Timeline'),
      name: 'blog timeine ',
      meta: {
        breadcrumb: true,
        title: '博客时间线'
        // icon: 'link'
      }
    },
    {
      path: '/blog/detail/:id',
      component: () => import('@/views/blog/detail'),
      name: 'blog detail',
      prop: { default: true },
      meta: {
        breadcrumb: true,
        title: '博客详情'
        // icon: 'link'
      }
    }
  ]
}
