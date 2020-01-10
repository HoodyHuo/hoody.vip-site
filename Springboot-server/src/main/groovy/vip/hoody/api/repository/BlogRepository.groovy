package vip.hoody.api.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import vip.hoody.api.domain.Blog

interface BlogRepository extends JpaRepository<Blog, String> {

    @Query(value = "select b.* from blog as b order by b.create_time desc limit 0, ? ", nativeQuery = true)
    List<Blog> getLastBlogs(num)

    @Query(value = "SELECT * from blog where MATCH(`content`,`title`) AGAINST(:query)",
            countQuery = "SELECT count(*) from blog where MATCH(`content`,`title`) AGAINST(:query)",
            nativeQuery = true)
    Page<Blog> findAllByQuery(@Param("query") String query, Pageable pageable)
}
