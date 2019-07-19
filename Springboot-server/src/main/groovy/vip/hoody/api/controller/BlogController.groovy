package vip.hoody.api.controller

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import jdk.nashorn.internal.parser.JSONParser
import org.apache.shiro.authz.annotation.RequiresGuest
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import springfox.documentation.spring.web.json.Json
import vip.hoody.api.domain.Blog
import vip.hoody.api.domain.Comment
import vip.hoody.api.exception.StorageException
import vip.hoody.api.service.BlogService
import vip.hoody.api.service.StorageService
import vip.hoody.api.service.impl.FileSystemStorageService
import vip.hoody.api.util.ResponseData
import vip.hoody.api.util.TimeUtil

import javax.servlet.http.HttpServletRequest
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@RestController
@RequestMapping("/blog")
class BlogController {

    @Autowired
    BlogService blogService

    /**
     * 获取博客列表
     * @param max 每页显示数
     * @param page 页数(从1开始)
     */
    @ApiImplicitParams([
            @ApiImplicitParam(name = "max", dataType = "Integer", example = "2", defaultValue = "10", value = "5"),
            @ApiImplicitParam(name = "page", dataType = "Integer", example = "2", defaultValue = "1", value = "1")
    ])
    @GetMapping("list")
    ResponseData list(@RequestParam("max") Integer max, @RequestParam("page") Integer page) {
        page = page == null ? 0 : page - 1 //JPA 分页从 0开始
        max = max == null ? 5 : max

        Page pageData = blogService.list(max, page)

        return new ResponseData(
                data: [
                        content   : pageData.content, //list of blog
                        totalPages: pageData.totalPages,
                        total     : pageData.totalElements // count of all blog
                ])
    }

    /**
     * 保存博客文本
     * @param blog
     * @param result
     * @return
     */
    @RequiresPermissions("blog:create")
//    @ApiImplicitParam(name = 'blog', dataTypeClass = Blog.class, required = true)
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    ResponseData deleteBlog(@RequestBody Map dataMap) {
        List<Long> ids = dataMap.ids
//        if(ids == null){
//            return  new ResponseData(code: 20001,"id 数组为空")
//        }
        blogService.delete(ids)
        return new ResponseData(data: ids)
    }

    /**
     * 获取博客对象
     * @param id
     * @return
     */
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    ResponseData getDetail(@PathVariable('id') Long id) {
        Blog blog = blogService.getDetail(id)
        if (blog == null) {
            return new ResponseData(code: 40400, msg: "")
        }
        return new ResponseData(data: blog)
    }

    @RequestMapping(value = "timeline", method = [RequestMethod.GET, RequestMethod.POST])
    ResponseData timeline() {
        List<Blog> blogs = blogService.getRecent()
        return new ResponseData(data: blogs)
    }
    /**
     * 保存博客文本
     * @param blog
     * @param result
     * @return
     */
    @RequiresPermissions("blog:create")
    @ApiImplicitParam(name = 'blog', dataTypeClass = Blog.class, required = true)
    @RequestMapping(value = "save", method = RequestMethod.POST)
    ResponseData save(@RequestBody Map dataMap) {
        Blog blog = new Blog()
        blog.id = dataMap?.id
        blog.title = dataMap?.title
        blog.content = dataMap?.content
        blog.createTime = TimeUtil.parseJsTimeStrToDate(dataMap?.createTime)
        blog.modifyTime = TimeUtil.parseJsTimeStrToDate(dataMap?.modifyTime)
        blogService.save(blog)
        return new ResponseData(data: blog.id)
    }
    /**
     * 上传blog 图片
     * TODO 未做，需要做多文件映射
     * @param files
     * @param request
     * @return
     */
    @RequiresPermissions("blog:create")
    @PostMapping("image/add")
    ResponseData upLoadImages(
            MultipartFile[] files
    ) {
        Map imgUrl = new HashMap()
        files.each { MultipartFile file ->
            String path = blogService.storeImage(file)
            imgUrl.put(file.originalFilename, path)
        }
        return new ResponseData(data: imgUrl)
    }

    @RequestMapping(value = "comments/list/{id}", method = RequestMethod.GET)
    ResponseData getComments(@PathVariable("id") Long blogId) {
        if (blogId == null) {
            return new ResponseData(code: 40001, msg: "缺少参数")
        }
        List<Comment> comments = blogService.getComments(blogId)
        return new ResponseData(data: comments)
    }

    @ApiImplicitParam(name = 'comment', dataTypeClass = Comment.class, required = true)
    @RequestMapping(value = "comment/save", method = RequestMethod.POST)
    ResponseData saveComment(Comment comment) {
        blogService.saveComment(comment)
        return new ResponseData(data: comment)
    }
}
