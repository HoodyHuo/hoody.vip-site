import request from './request'

/** 获取博客信息 */
export function getBlog(id) {
  return request({
    url: 'blog/detail/' + id,
    method: 'get'
  })
}
/** 获取博客列表 */
export function getList(page, max) {
  return request({
    url: 'blog/list',
    method: 'get',
    params: { page, max }
  })
}
/** 提交评论 */
export function saveComment(comment) {
  return request({
    url: 'blog/comment/save',
    method: 'post',
    params: comment
  })
}
/** 获取指定博客的评论 */
export function getCommets(id) {
  return request({
    url: 'blog/comments/list/' + id,
    method: 'get'
  })
}
/** 获取评论的追评 */
export function getCommetsReply(commentsId) {
  return request({
    url: 'blog/comments/reply/' + commentsId,
    method: 'get'
  })
}

