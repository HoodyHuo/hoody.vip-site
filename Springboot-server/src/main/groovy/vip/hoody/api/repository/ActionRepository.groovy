package vip.hoody.api.repository

import vip.hoody.api.domain.Action
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Hoody on 2019/1/15.
 */
interface ActionRepository extends JpaRepository<Action, Long> {

}