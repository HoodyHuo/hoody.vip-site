# 个人博客 [Hoody's blog](http://www.hoody.vip) 项目

主要采用Springboot应用做后端,Nuxt JS 做前端,便于SSR,Vue-cli3 做管理端页面

## 目录介绍
|目录|描述|文档|
|-|-|-|
|Springboot-Server|基于Springboot的后端项目(Maven)|[README.md](https://github.com/HoodyHuo/hoody.vip-site/tree/master/Springboot-server)
|ssr-front|Nuxt js 框架 的博客前端,服务器端渲染,便于搜索引擎收录|[README.md](https://github.com/HoodyHuo/hoody.vip-site/tree/master/ssr-front)
|vue-admin|vue-cli3 的前端管理项目|[README.md](https://github.com/HoodyHuo/hoody.vip-site/tree/master/vue-admin)
|deploy.cmd & ScpScript.txt |项目编译与上传服务器脚本|


# 编译脚本

`deploy.bat`
```
请选择需要执行的内容:
1:build ssr-Front
2:build vue-Admin
3:build Springboot-API
4:build All and deploy to ECS
5:quit 
```
发布至服务器需要本机安装WinSCP,并将其安装目录加入环境变量Path
  
## Browsers support

Modern browsers and Internet Explorer 10+.

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="IE / Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>IE / Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Safari |
| --------- | --------- | --------- | --------- |
| IE10, IE11, Edge| last 2 versions| last 2 versions| last 2 versions

## License

[MIT](https://github.com/HoodyHuo/hoody.vip-api/blob/master/LICENSE) license.

Copyright (c) 2019 HoodyHuo 