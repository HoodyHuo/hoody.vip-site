## 简介
前后端分离，前端只利用Json来和后端进行交互  
后端不返回页面，只返回Json数据。  

包含:
> - Swagger2 ui 进行API 测试
> - Groovy 混合开发(主要用于Controller和service)
>-  JPA + MySQL 数据处理
>-  Shiro 权限控制 
#
## Groovy 

如果通过IDE进行编辑,需要配置源码目录包含`/src/main/groovy` 目录

#
## Shiro
### 自定义Session获取方式  

配置
````
  @Bean
    public SessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        return customSessionManager;
    }
````

`CustomSessionManager.java`
````
class CustomSessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "X-Token"

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request"

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION)
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE)
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id)
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE)
            return id
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response)
        }
    }
}
````
#
## Swagger2 api

需要注意  
项目发布后通过Nginx转发请求时  
Swagger-UI测试页面上发送请求时报404错误,
是因为Swagger-UI不能正确找到API的url地址.
#### 需要如下配置  
`application.propeties`
````
    #比如我挂载目录是'/aappii'
    server.servlet.context-path= '/aappii'
````
`nginx.conf`
````
#server 指向springboot
location ^~ /server/ { 
        proxy_set_header   Host                    $host;
        roxy_set_header    X-Real-IP               $remote_addr;
        proxy_set_header   X-Forwarded-For         $proxy_add_x_forwarded_for;
        proxy_set_header   X-Forwarded-Proto       $scheme;
        proxy_set_header   Referer $http_referer;
        
        #将所有service转到本地端口的8080/aappii
        rewrite  ^.+server/?(.*)$ /$1 break; 
        proxy_pass http://localhost:8080/aappii/;
 	}
````
则需要在
`application.propeties`
````
    #比如我挂载目录是'/aappii'
    server.servlet.context-path= '/aappii'
    
    #解决Swagger-UI 请求地址生成的问题，需要指向Nginx代理后的地址加api
    springfox.documentation.swagger.v2.host= server/api
````


