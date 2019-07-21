package vip.hoody.api.acpect

import org.apache.shiro.authc.UsernamePasswordToken;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 登录切面类
 */
@Aspect
@Component
public class LoginAspect {
    private static final Logger log = LoggerFactory.getLogger(LoginAspect.class);

    /** 记录登录信息 */
    @AfterReturning("execution(* vip.hoody.api.controller.AuthController.login(org.apache.shiro.authc.UsernamePasswordToken,..))&&args(token,..)")
    public void logLogin(UsernamePasswordToken token) {
        log.warn("login:-------${token.username}")
    }

}

