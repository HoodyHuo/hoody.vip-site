/**
 * 封装Axios
 * 1.给所有请求增加X-Token作为权限
 * 2.处理所有请求、响应错误信息
 * 3.
 */

import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['X-Token'] = getToken()
    }
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
      // Message({
      //   message: res.message || '操作成功',
      //   type: 'success',
      //   duration: 1 * 1000
      // })
      return res
    } else {
      // if the custom code is not 200, it is judged as an error.
      Message({
        message: res.msg || 'Error',
        type: 'error',
        duration: 2 * 1000
      })

      // 40301: Illegal token; 40302: Other clients logged in; 40303: Token expired;
      if (res.code === 40301 || res.code === 40302 || res.code === 40303 || res.code === 40304) {
        // to re-login
        MessageBox.confirm('您不具备权限或登录信息已失效,点击【登录】进行重新登录,点击取消将会停留在此页面', '权限确认', {
          confirmButtonText: '登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.msg || 'Error'))
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.msg,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
