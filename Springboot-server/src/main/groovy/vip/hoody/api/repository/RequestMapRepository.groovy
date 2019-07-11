package vip.hoody.api.repository

import vip.hoody.api.domain.RequestMap
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Hoody on 2019/1/17.
 */
interface RequestMapRepository extends JpaRepository<RequestMap, Long> {

    List<RequestMap> findAllByConfigAttribute(String configAttribute)

    RequestMap findByUrl(String url)

}