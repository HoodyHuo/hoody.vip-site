package vip.hoody.api.repository

import org.springframework.data.jpa.repository.JpaRepository
import vip.hoody.api.domain.Comment

import org.springframework.data.jpa.repository.Query

interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c.* from `comment` as c WHERE c.blog_id= ? and ISNULL(c.reply_to)order by c.floor asc", nativeQuery = true)
    List<Comment> findAllbyBlogId(Long blogId)

    @Query(value = "select c.* from `comment` as c WHERE c.reply_to = ? order by c.floor asc ", nativeQuery = true)
    List<Comment> findAllbyReplyTo(Long replyTo)
}