const { resolve } = require('path')
export default {
  mode: 'universal',
  server: {
    port: process.env.NODE_ENV === 'production' ? 80 : 80, // default: 3000
    host: '0.0.0.0', // default: localhost,
    timing: {
      total: true
    }
  },
  /*
  ** Headers of the page
  */
  head: {
    title: process.env.npm_package_name || '',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: process.env.npm_package_description || '' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
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
  ],
  /*
  ** Nuxt.js dev-modules
  */
  buildModules: [
    // Doc: https://github.com/nuxt-community/eslint-module
    '@nuxtjs/eslint-module'
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
  ** Axios module configuration
  ** See https://axios.nuxtjs.org/options
  */
  axios: {
  },
  /*
  ** Build configuration
  */
  build: {
    transpile: [/^element-ui/],
    devtools: true,
    // 增加打包分析
    analyze: {
      analyzerMode: 'static'
    },
    optimization: {
      minimize: true,
      moduleIds: 'named',
      minimizer: [
      // terser-webpack-plugin
      // optimize-css-assets-webpack-plugin
      ],
      splitChunks: {
        chunks: 'all',
        automaticNameDelimiter: '.',
        name: undefined,
        cacheGroups: {}
      }
    },

    /*
    ** You can extend webpack config here
    */
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
