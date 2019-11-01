package vip.hoody.api.elasticsearch

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import vip.hoody.api.domain.Blog

//indexName 相当于表名,
@Document(indexName = "blog")
class EsBlog {
    //ID是必须的注解
    @Id
    private String id
    private String title
    private String content
    private Date createTime
    private Date modifyTime

    public static EsBlog convert(Blog blog) {
        EsBlog esBlog = new EsBlog()
        esBlog.id = blog.id
        esBlog.content = blog.content
        esBlog.title = blog.title
        esBlog.createTime = blog.createTime
        esBlog.modifyTime = blog.modifyTime
        return esBlog
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }

    Date getCreateTime() {
        return createTime
    }

    void setCreateTime(Date createTime) {
        this.createTime = createTime
    }

    Date getModifyTime() {
        return modifyTime
    }

    void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EsBlog.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("content='" + content + "'")
                .add("createTime=" + createTime)
                .add("modifyTime=" + modifyTime)
                .toString();
    }
}
