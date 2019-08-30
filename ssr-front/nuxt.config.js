
const { resolve } = require('path')

export default {

  server: {
    port: process.env.NODE_ENV === 'production' ? 80 : 80, // default: 3000
    host: '0.0.0.0', // default: localhost,
    timing: {
      total: true
    }
  },
  // 通用头部信息
  head: {
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' }
    ]
  },
  mode: 'universal',
  env: {
  },
  /*
  ** Customize the progress-bar color
  */
  loading: { color: '#fff' },
  /*
  ** Global CSS
  */
  css: [
    'element-ui/lib/theme-chalk/index.css',
    'assets/page-transfer.css'
  ],
  /*
  ** Plugins to load before mounting the App
  */
  plugins: [
    '@/plugins/element-ui',
    '@/plugins/svg-icon'
    // '@/plugins/axios'
  ],
  /*
  ** Nuxt.js modules
  */
  modules: [
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    '@nuxtjs/eslint-module',
    '@nuxtjs/proxy'
  ],
  proxy: {
    '/api/': {
      target: process.env.NODE_ENV === 'production' ? 'http://114.55.171.14/api' : 'http://localhost:8080',
      // target: 'http://localhost:8080',
      pathRewrite: {
        '^/api/': process.env.NODE_ENV !== 'production' ? '' : '/api'
      },
      secure: false
    },
    '/storage/': {
      target: process.env.NODE_ENV === 'production' ? 'http://hoody.vip' : 'http://localhost:8080',
      secure: false
    }
  },
  router: {
    middleware: 'breadcrumb'
  },
  /*
  ** Build configuration
  */
  build: {
    transpile: [/^element-ui/],
    /*
    ** You can extend webpack config here
    */
    loaders: [
    ],
    extend(config, ctx) {
      // //   // 排除 nuxt 原配置的影响
      const svgRule = config.module.rules.find(rule => rule.test.test('.svg'))
      svgRule.exclude = [resolve(__dirname, 'assets/icons/svg')]

      config.module.rules.push({
        test: /\.svg$/,
        include: [resolve(__dirname, 'assets/icons/svg')],
        use: [
          { loader: 'svg-sprite-loader',
            options: {
              extract: false,
              symbolId: 'icon-[name]'
              // publicPath: '/_nuxt/'
            }
          }
        ]
      })
    }
  }
}
