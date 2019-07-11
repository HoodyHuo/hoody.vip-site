import request from '@/utils/request'

/**
 * 获取博客对象
 * @param id
 */
export function getBlog(id) {
  return request({
    url: 'blog/detail/' + id,
    method: 'get'
  })
}

export function deleteBlog(ids) {
  return request({
    url: 'blog/delete',
    method: 'post',
    data: { ids }
  })
}

export function getList(page, max) {
  return request({
    url: 'blog/list',
    method: 'get',
    params: { page, max }
  })
}

export function addImage(data) {
  return request({
    url: 'blog/image/add',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    files: data
  })
}

export function deletImage(data) {
  return request({
    url: 'blog/image/delete',
    method: 'post',
    data
  })
}

export function save(data) {
  return request({
    url: 'blog/save',
    method: 'post',
    data: data
  })
}
