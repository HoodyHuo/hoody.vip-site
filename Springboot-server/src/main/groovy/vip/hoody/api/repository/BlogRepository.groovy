package vip.hoody.api.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import vip.hoody.api.domain.Blog

interface BlogRepository extends JpaRepository<Blog,Long> {

    @Query(value = "select b.* from blog as b order by b.create_time desc limit 0, ? ",nativeQuery = true)
    List<Blog> getLastBlogs(num)
}