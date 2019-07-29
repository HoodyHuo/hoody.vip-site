package vip.hoody.api.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import vip.hoody.api.service.SeoService

/**
 * 为搜索引擎爬虫
 */
@RestController
class SeoController {
    SeoService seoService

    @Autowired
    SeoController(SeoService seoService) {
        this.seoService = seoService
    }
    /**
     * 生成sitemap
     * @param baseUrl
     * @return
     */
    @RequestMapping(value = "/sitemap.xml",
            produces = "application/xml;charset=UTF-8",
            method = RequestMethod.GET)
    String getSitemap() {
        String sitemapXML = seoService.getSitemapXml()
        return sitemapXML
    }

}
