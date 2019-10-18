package vip.hoody.api.acpect;

import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import vip.hoody.api.domain.Blog
import vip.hoody.api.domain.Comment
import vip.hoody.api.service.BlogService
import vip.hoody.api.service.IEmailService
import vip.hoody.api.service.impl.SrpingEmailServiceImpl;

/**
 * 邮件切面类
 * todo 美化邮件内容
 */
@Aspect
@Component
public class EmailAspect {


    private IEmailService emailService
    private BlogService blogService

    @Autowired
    public EmailAspect(SrpingEmailServiceImpl emailService, BlogService blogService) {
        this.emailService = emailService
        this.blogService = blogService
    }


    @After("execution(* vip.hoody.api.controller.BlogController.saveComment(vip.hoody.api.domain.Comment))&&args(comment)")
    void sendEmailToReply(Comment comment) {
        println(comment)
        if (comment.replyTo == null) {
            return
        }
        Comment commentTo = blogService.getComment(comment.replyTo)
        Blog blog = blogService.getDetail(comment.blogId)
        emailService.sendSimpleMail(commentTo.email,
                "您的评论有新回复了-Hoody's Blog",
                """
    您在 《${blog.title}-Hoody's Blog》 的评论有新回复了，

${comment.name}回复到：${comment.content}


请前往以下链接查看:
    http://hoody.vip/blog/detail/${blog.id}
""")
    }

}
