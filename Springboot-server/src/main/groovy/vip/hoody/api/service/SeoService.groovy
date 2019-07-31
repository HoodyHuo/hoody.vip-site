package vip.hoody.api.service

import groovy.xml.StreamingMarkupBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import vip.hoody.api.domain.Blog

import java.text.SimpleDateFormat

/**
 * SEO
 */
@Service
class SeoService {
    /** 项目发布网址eg. hoody.vip */
    private String baseUrl
    private BlogService blogService
    private SimpleDateFormat w3cDateFormat

    SeoService(@Value('${platform.baseUrl}') String baseUrl, BlogService blogService) {
        this.baseUrl = baseUrl
        this.blogService = blogService
        this.w3cDateFormat = new SimpleDateFormat("YYYY-MM-dd")
    }

    /**
     * 组装sitemap.xml 字符串
     * @return
     */
    String getSitemapXml() {
        def sw = new StringWriter()
        def urls = []
        urls.addAll(this.getBlogUrls())
        urls.addAll(getWelcome())
        /** 创建xml闭包*/
        def xml = {
            mkp.xmlDeclaration()
            urlset(xmlns: "http://www.sitemaps.org/schemas/sitemap/0.9") {
                /** 循环添加闭包子节点 */
                urls.collect { item ->
                    url {
                        loc(item.loc)
                        /** 过滤信息,不存在则不添加 */
                        if (item.lastmod != null) {
                            lastmod(item.lastmod)
                        }
                        if (item.priority != null) {
                            priority(item.priority)
                        }
                    }
                }
            }
        }
        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        sw << builder.bind(xml)
        return sw.toString()
    }

    /**
     * 组装博客sitemap信息
     * @return
     */
    def getBlogUrls() {
        def urls = []
        List<Blog> blogs = blogService.getBlogRepository().findAll()
        blogs.each { Blog blog ->
            Map<String, String> url = [
                    loc     : "http://${baseUrl}/blog/detail/${blog.id}/",
                    lastmod : w3cDateFormat.format(blog.modifyTime),
                    priority: "0.9"
            ]
            urls << url
        }
        return urls
    }

    /**
     * 组装欢迎sitemap信息
     * @return
     */
    def getWelcome() {
        Map<String, String> url = [
                loc: "http://${baseUrl}/",
        ]
        return url
    }

}
