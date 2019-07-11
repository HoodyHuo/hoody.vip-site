/**
 * 模拟个人博客数据
 */

const blogs = [
  {
    sid: 1,
    time: '2019年6月1日',
    title: '1初次创建页面',
    content: `
    >a
    b
    sdfasdfadsf
    `
  },
  {
    sid: 2,
    time: '2019年6月6日',
    title: '2初次创建页面',
    content: `
    >a2
    b233333
    sdfasdfadsf2`
  },
  {
    sid: 3,
    time: '2019年6月3日',
    title: '3初次创建页面',
    content: `
    >a2
    b233333
    sdfasdfadsf2`
  },
  {
    sid: 4,
    time: '2019年6月4日',
    title: '4初次创建页面',
    content: `
    >a2
    b2444444 44
    sdfasdfadsf2`
  }
]

export default [

  {
    url: '/blog/timeline',
    type: 'post',
    response: config => {
      return {
        code: 20000,
        data: blogs
      }
    }
  },

  {
    url: '/blog/detail',
    type: 'post',
    response: config => {
      const { sid } = config.body
      for (const blog of blogs) {
        if (blog.sid + '' === sid + '') {
          return {
            code: 20000,
            data: blog
          }
        }
      }
      return {
        code: 20001
      }
    }
  }
]
