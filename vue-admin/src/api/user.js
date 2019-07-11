import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    params: data
  })
}

export function getInfo(token) {
  return request({
    url: '/auth/info',
    method: 'post',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}
