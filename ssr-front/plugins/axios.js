import { Message } from 'element-ui'
export default function({ $axios, redirect }) {
  // request interceptor
  $axios.interceptors.request.use(
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
  $axios.onRequest(config => {
    console.log('Making request to ' + config.url)
  })

  // response interceptor
  $axios.interceptors.response.use(
    /**
     * Determine the request status by custom code
     * Here is just an example
     * You can also judge the status by HTTP Status Code
     */
    response => {
      const res = response.data
      if (res.code === 20000) {
        return res
      } else {
        redirect('/404')
        // if the custom code is not 200, it is judged as an error.
      }
      return Promise.reject(new Error(res.msg || 'Error'))
    },
    error => {
      console.log('err' + error) // for debug
      if (process.client) {
        Message({
          message: error.message,
          type: 'error',
          duration: 5 * 1000
        })
      }
      return Promise.reject(error)
    }
  )

  $axios.onError(error => {
    const code = parseInt(error.response && error.response.status)
    if (code === 400) {
      redirect('/404')
    } else if (code === 500) {
      redirect('/500')
    }
  })
}
