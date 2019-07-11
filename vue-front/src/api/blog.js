import request from '@/utils/request'

export function timeline() {
  return request({
    url: 'blog/timeline',
    method: 'post'
  })
}

export function getBlog(id) {
  return request({
    url: 'blog/detail/' + id,
    method: 'get'
  })
}
