package vip.hoody.api.domain

import javax.persistence.*

/**
 * Created by Hoody on 2019/1/17.
 */
@Entity
@Table(name = "request_map")
class RequestMap {

    @Id
    @GeneratedValue
    Long id

    String configAttribute

    String url

    @OneToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id", nullable = true)
    Action action



    @Override
    public String toString() {
        return "RequestMap{" +
                "id=" + id +
                ", configAttribute='" + configAttribute + '\'' +
                ", url='" + url + '\'' +
                ", action=" + action.toString() +
                '}';
    }
}
