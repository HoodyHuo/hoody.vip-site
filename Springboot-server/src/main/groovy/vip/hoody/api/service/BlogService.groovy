package vip.hoody.api.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import vip.hoody.api.domain.Blog
import vip.hoody.api.domain.Comment
import vip.hoody.api.repository.BlogRepository
import vip.hoody.api.repository.CommentRepository
import vip.hoody.api.util.MimeTypeUtil

@Service
class BlogService {

    StorageService storageService

    BlogRepository blogRepository

    CommentRepository commentRepository

    @Autowired
    BlogService(StorageService storageService, BlogRepository blogRepository, CommentRepository commentRepository) {
        this.storageService = storageService
        this.blogRepository = blogRepository
        this.commentRepository = commentRepository
    }

    Page list(int max, int page) {
        Pageable pageable = new PageRequest(page, max, Sort.Direction.DESC, "createTime")
        def data = blogRepository.findAll(pageable)
        return data
    }

    Blog getDetail(Long id) {
        return blogRepository.findById(id).get()
    }

    void delete(List<Long> ids) {
        ids.each { Long id ->
            blogRepository.deleteById(id)
        }
    }

    List<Blog> getRecent(Integer num) {
        num = num == null ? 5 : num
        Pageable pageable = new PageRequest(0, num, Sort.Direction.DESC, "createTime")
        def data = blogRepository.findAll(pageable)
        return data.content
    }

    Blog save(Blog blog) {
        if (blog.id != null) {
            Blog sotrageBlog = blogRepository.findById(blog.id).get()
            if (sotrageBlog != null) {
                blog.createTime = sotrageBlog.createTime
            }
        }
        return blogRepository.save(blog)
    }

    List<Comment> getComments(Long blogId) {
        List<Comment> list = commentRepository.findAllbyBlogId(blogId)
        list.each { Comment comment ->
            comment.replyComments = commentRepository.findAllbyReplyTo(comment.id)
        }
        return list
    }

    Comment saveComment(Comment comment) {
        commentRepository.save(comment)
        return comment
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
