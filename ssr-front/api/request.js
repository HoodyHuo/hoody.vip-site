/**
 * 封装Axios
 * 2.处理所有请求、响应错误信息
 */
import { Message } from 'element-ui'
import axios from 'axios'
// create an axios instance
const service = axios.create({
  baseURL: process.env.NODE_ENV === 'production' ? '/api/' : '/api/', // 所有请求,都通过Nuxt进行代理
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data

    if (res.code === 20000) {
    //   Message({
    //     message: res.message || '操作成功',
    //     type: 'success',
    //     duration: 1 * 1000
    //   })
      return res
    } else {
      // if the custom code is not 200, it is judged as an error.
      Message({
        message: res.msg || 'Error',
        type: 'error',
        duration: 2 * 1000
      })
      return Promise.reject(new Error(res.msg || 'Error'))
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
