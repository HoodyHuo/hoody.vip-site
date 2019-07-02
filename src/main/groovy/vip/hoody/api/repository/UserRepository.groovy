package vip.hoody.api.repository

import vip.hoody.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Hoody on 2019/1/14.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
