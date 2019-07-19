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

export function saveComment(comment) {
  return request({
    url: 'blog/comment/save',
    method: 'post',
    params: comment
  })
}
export function getCommets(id) {
  return request({
    url: 'blog/comments/list/' + id,
    method: 'get'
  })
}
