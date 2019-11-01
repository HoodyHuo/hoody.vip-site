package vip.hoody.api.elasticsearch

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.test.context.junit4.SpringRunner
import vip.hoody.api.domain.Blog
import vip.hoody.api.service.BlogService
import vip.hoody.api.service.EsBlogService


@RunWith(SpringRunner.class)
@SpringBootTest()
class EsBlogTest {

    @Autowired
    EsBlogService service

    @Autowired
    BlogService blogService

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate

    @Test
    public void saveTest() {
        Blog blog = new Blog()
        blog.id = 12345L
        blog.title = "这是222标题"
        blog.content = "这是一个，全兴的内容"
        blog.createTime = new Date()

        EsBlog esBlog = EsBlog.convert(blog)
        service.save(esBlog)

        Blog blog2 = new Blog()
        blog2.id = 23L
        blog2.title = "这是大姐夫题"
        blog2.content = "这是一个，水电是打发斯蒂芬费"
        blog2.createTime = new Date()

        EsBlog esBlog2 = EsBlog.convert(blog2)
        service.save(esBlog2)
    }

    @Test
    public void searchTest() {
        List<EsBlog> list = service.search("搜索")
        list.title.each { println(it) }
    }

    @Test
    public void cleanData() {
        service.esBlogDao.deleteAll()
    }


    @Test
    public void transData() {
        List<Blog> org = blogService.list(100, 0, null).getContent()
        List<EsBlog> dis = org.collect { EsBlog.convert(it) }
        dis.each { service.save(it) }
    }

    @Test
    public void hilightSearch() {
        String str = "编译"
        Page<EsBlog> queryForPage = service.searchHighlight(10, 0, str)
        List<EsBlog> list = queryForPage.getContent()
        println(list.size())
        println(queryForPage.totalElements)
        println(queryForPage.totalPages)
    }

}
