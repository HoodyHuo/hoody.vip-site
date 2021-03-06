package vip.hoody.api.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import vip.hoody.api.domain.Blog
import vip.hoody.api.domain.Comment
import vip.hoody.api.elasticsearch.EsBlog
import vip.hoody.api.repository.BlogRepository
import vip.hoody.api.repository.CommentRepository
import vip.hoody.api.util.MimeTypeUtil

@Service
@Transactional
class BlogService {

    StorageService storageService

    BlogRepository blogRepository

    CommentRepository commentRepository

    EsBlogService esBlogService

    @Autowired
    BlogService(StorageService storageService, EsBlogService esBlogService, BlogRepository blogRepository, CommentRepository commentRepository) {
        this.storageService = storageService
        this.blogRepository = blogRepository
        this.commentRepository = commentRepository
        this.esBlogService = esBlogService
    }

    /**
     * 获取博客列表(分页)
     * @param max 每页数量
     * @param page 页数
     * @return
     */
    Page list(int max, int page, String query) {

        Page data = null
        if (query == null) {
            Pageable pageable = new PageRequest(page, max, Sort.Direction.DESC, "createTime")
            data = blogRepository.findAll(pageable)
        } else {
            data = esBlogService.searchHighlight(max, page, query)
        }
        return data
    }

    /**
     * 获取博客信息
     * @param id
     * @return
     */
    Blog getDetail(String id) {
        Optional<Blog> blog = blogRepository.findById(id)
        return blog.isPresent() ? blog.get() : null

    }

    /**
     * 删除博客
     * @param ids 需要删除的博客ID List
     */
    @Modifying
    void delete(List<String> ids) {
        ids.each { String id ->
            blogRepository.deleteById(id)
            commentRepository.deleteByBlogId(id)
            esBlogService.delete(id)
        }
    }


    /**
     * 保存\更新博客
     * @param blog
     * @return
     */
    Blog save(Blog blog) {
        if (blog.id != null) {
            Blog sotrageBlog = blogRepository.findById(blog.id).get()
            if (sotrageBlog != null) {
                blog.createTime = sotrageBlog.createTime
            }
        }
        esBlogService.save(EsBlog.convert(blog))
        return blogRepository.save(blog)
    }

    /**
     * 获取指定博客的评论信息
     * @param blogId
     * @return
     */
    List<Comment> getComments(String blogId) {
        List<Comment> list = commentRepository.findAllbyBlogId(blogId)
        list.each { Comment comment ->
            comment.replyComments = commentRepository.findAllbyReplyTo(comment.id)
        }
        return list
    }

    /**
     * 保存评论
     * @param comment
     * @return
     */
    Comment saveComment(Comment comment) {
        comment.floor = 1 + commentRepository.countByBlogIdAndReplyTo(comment.blogId, comment.replyTo)
        commentRepository.save(comment)
        return comment
    }

    /**
     * 获取指定评论的追评
     * @param commentId
     * @return
     */
    List<Comment> getCommentsReply(Long commentId) {
        return commentRepository.findAllbyReplyTo(commentId)
    }

    /**
     * 获取评论信息
     * @param commentId
     * @return
     */
    Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).get()
    }
    /**
     *  保存图片文件
     * @param imageFile
     * @return String 图片的url地址
     */
    String storeImage(MultipartFile file) {
        String fileName = storageService.store(file,
                '/blog',
                MimeTypeUtil.getSuffixByMimeType(file.getContentType()))
        return fileName
    }
}
