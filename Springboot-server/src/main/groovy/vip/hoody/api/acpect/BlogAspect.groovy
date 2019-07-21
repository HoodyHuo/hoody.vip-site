package vip.hoody.api.acpect

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import vip.hoody.api.exception.ProjectException

import javax.servlet.http.HttpServletRequest


/**
 * 博客Blog 切面类
 */
@Aspect
@Component
class BlogAspect {

    /** 存放IP地址与博客评论提交时间 */
    private Map<String, Long> cache
    /** 评论间隔 */
    private Long saveInterval

    /**
     * 构造函数
     * @param saveInterval 评论提交间隔,默认60秒
     */
    BlogAspect(@Value('${platform.blog.comment.saveInterval:60000}') Long saveInterval) {
        this.cache = new HashMap<String, Long>()
        this.saveInterval = saveInterval
    }

    /**
     * AOP 切入保存评论方法
     * 如果提交间隔低于配置,则抛出异常,中断保存
     */
    @Before("execution(* vip.hoody.api.controller.BlogController.saveComment(..))")
    void doBeforeSaveComment() {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        checkFrequency(request.getRemoteAddr())
        println("getRemoteAddr:${request.getRemoteAddr()}")
        println("X-Forwarded-For:${request.getHeader('X-Forwarded-For')}")
        println(request)
    }

    /**
     * 检查指定IP提交间隔是否大于配置
     * @param ip
     */
    private void checkFrequency(String ip) {
        Long lastTime = cache.get(ip)
        if (lastTime != null && (new Date().getTime() - lastTime) < saveInterval) {
            throw new ProjectException("评论提交间隔时间要大于${saveInterval / 1000}秒")
        }
        cache.put(ip, new Date().getTime())
    }
}
