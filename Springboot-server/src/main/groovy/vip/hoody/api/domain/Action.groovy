package vip.hoody.api.domain

import javax.persistence.*

/**
 * Created by Hoody on 2019/1/15.
 */
@Entity
@Table(name="action")
class Action implements Serializable{

    @Id
    @Column(name="id")
    @GeneratedValue
    private Long id

    @Column(name='title')
    private String title

    @Column(name='controller_name')
    private String controllerName

    @Column(name='is_menu')
    private boolean isMenu

    @Column(name='sort_num')
    private int sortNum

    @Column(name='group_name')
    private String groupName


    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }

    String getControllerName() {
        return controllerName
    }

    void setControllerName(String controllerName) {
        this.controllerName = controllerName
    }

    boolean getIsMenu() {
        return isMenu
    }

    void setIsMenu(boolean isMenu) {
        this.isMenu = isMenu
    }

    int getSortNum() {
        return sortNum
    }

    void setSortNum(int sortNum) {
        this.sortNum = sortNum
    }

    String getGroupName() {
        return groupName
    }

    void setGroupName(String groupName) {
        this.groupName = groupName
    }

    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", controllerName='" + controllerName + '\'' +
                ", isMenu=" + isMenu +
                ", sortNum=" + sortNum +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
