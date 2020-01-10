package vip.hoody.api.service

import org.elasticsearch.index.query.QueryStringQueryBuilder
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder
import org.elasticsearch.search.sort.SortBuilders
import org.elasticsearch.search.sort.SortOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.core.query.SearchQuery
import org.springframework.stereotype.Service
import vip.hoody.api.elasticsearch.EsBlog
import vip.hoody.api.elasticsearch.EsBlogDao
import vip.hoody.api.elasticsearch.EsBlogSearchResultMapper

@Service
class EsBlogService {

    @Autowired
    EsBlogDao esBlogDao

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate

    public void save(EsBlog blog) {
        esBlogDao.save(blog)
    }

    public void delete(String id) {
        esBlogDao.deleteById(id)
    }

    public List<EsBlog> search(String str) {
        List<EsBlog> list = esBlogDao.findAllByContent(str)
        return list
    }

    /**
     *
     * @param max
     * @param page
     * @param searchStr
     * @return
     */
    public AggregatedPage<EsBlog> searchHighlight(int max, int page, String searchStr) {
        // 定义高亮字段
        HighlightBuilder.Field titleField = new HighlightBuilder.Field("title").preTags('<hits>').postTags("</hits>");
        HighlightBuilder.Field contentField = new HighlightBuilder.Field("content").preTags('<hits>').postTags("</hits>");
        // 构建查询内容
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(searchStr)
        // 查询匹配的字段
        queryBuilder.field("title").field("content");

        //排序器
        def sorter = SortBuilders.scoreSort().order(SortOrder.DESC)

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withHighlightFields(titleField, contentField)
                .withPageable(PageRequest.of(page, max))
                .withSort(sorter)
                .build()

        AggregatedPage<EsBlog> queryForPage =
                elasticsearchTemplate.queryForPage(
                        searchQuery,
                        EsBlog.class,
                        new EsBlogSearchResultMapper())
        return queryForPage
    }

}
