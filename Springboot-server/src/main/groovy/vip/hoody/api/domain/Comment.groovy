package vip.hoody.api.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = 'comment')
@EntityListeners(AuditingEntityListener.class)
class Comment implements Serializable {

    /**
     * ID
     */
    @Id
    @Column(name = 'id')
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id

    @Column(name = 'blog_id')
    private Long blogId

    /** 评论人称呼*/
    @Column(name = 'name')
    private String name

    /** 评论人邮箱 */
    @Column(name = 'email')
    private String email
    /** 评论内容 */
    @Column(name = 'content')
    private String content

    /** 评论楼层 */
    @Column(name = 'floor')
    private Integer floor

    /** 如果是回复评论,回复的是那条CommentID */
    @Column(name = 'reply_to')
    private Long replyTo

    @CreatedDate
    @Column(name = "create_time")
    private Date createTime

    @Transient
    private List<Comment> replyComments

    Long getReplyTo() {
        return replyTo
    }

    void setReplyTo(Long replyTo) {
        this.replyTo = replyTo
    }

    List<Comment> getReplyComments() {
        return replyComments
    }

    void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments
    }

    Long getBlogId() {
        return blogId
    }

    void setBlogId(Long blogId) {
        this.blogId = blogId
    }

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    Integer getFloor() {
        return floor
    }

    void setFloor(Integer floor) {
        this.floor = floor
    }

    Date getCreateTime() {
        return createTime
    }

    void setCreateTime(Date createTime) {
        this.createTime = createTime
    }

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", blogId='" + blogId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", floor=" + floor +
//                ", replyto=" + replyto +
                ", createTime=" + createTime +
                ", replyComments=" + replyComments +
                '}';
    }
}
