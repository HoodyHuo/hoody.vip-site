package vip.hoody.api.elasticsearch

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

/**
 * Elasticsearch DAO for EsBlog
 */
interface EsBlogDao extends ElasticsearchRepository<EsBlog, String> {
    public List<EsBlog> findAllByContent(String keyWord)
}
