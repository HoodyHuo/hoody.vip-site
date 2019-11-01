package vip.hoody.api.elasticsearch

import com.alibaba.fastjson.JSONObject
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.common.text.Text
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.SearchResultMapper
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl

/**
 * EsBlog Highlight search SearchResultMapper
 */
class EsBlogSearchResultMapper implements SearchResultMapper {
    @Override
    def <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        List<EsBlog> list = new ArrayList<EsBlog>();
        for (SearchHit searchHit : response.getHits()) {
            // put hit data to esBlog
            EsBlog esBlog = JSONObject.parseObject(searchHit.getSourceAsString(), EsBlog.class);
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            //if "title" field hits,replace marked string to esblog.title
            HighlightField titleHighlight = highlightFields.get("title");
            if (titleHighlight != null) {
                Text[] fragments = titleHighlight.fragments();
                String fragmentString = fragments[0].string();
                esBlog.setTitle(fragmentString);
            }
            //if "content" field hits,replace marked string to esblog.content
            HighlightField contentHighlight = highlightFields.get("content");
            if (contentHighlight != null) {
                Text[] fragments = contentHighlight.fragments();
                String fragmentString = ""
                fragments.each {
                    fragmentString += it.string() + "..."
                }
                esBlog.setContent(fragmentString);
            }
            list.add(esBlog)
        }
        return new AggregatedPageImpl<T>((List<T>) list, pageable, response.getHits().getTotalHits())
    }
}
