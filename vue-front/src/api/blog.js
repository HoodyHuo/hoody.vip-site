import request from '@/utils/request'

export function getBlog(id) {
  return request({
    url: 'blog/detail/' + id,
    method: 'get'
  })
}
export function getList(page, max) {
  return request({
    url: 'blog/list',
    method: 'get',
    params: { page, max }
  })
}
