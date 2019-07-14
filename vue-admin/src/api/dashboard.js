import request from '@/utils/request'

export function last5MinData() {
  return request({
    url: '/dashboard/info/last',
    method: 'post'
  })
}
