package vip.hoody.api.domain

import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = 'blog')
@EntityListeners(AuditingEntityListener.class)
class Blog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = 'id')
    private String id

    @NotNull
    @Column(name = 'title')
    private String title

    @NotNull
    @Column(name = 'content')
    private String content

    @CreatedDate
    @Column(name = "create_time")
    private Date createTime

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column(name = "modify_time")
    private Date modifyTime;


    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title=" + title +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
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
}
