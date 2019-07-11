package vip.hoody.api.acpect

import org.apache.shiro.authc.UsernamePasswordToken;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 登录切面类
 */
@Aspect
@Component
public class LoginAspect {


    @AfterReturning("execution(* vip.hoody.api.controller.AuthController.login(org.apache.shiro.authc.UsernamePasswordToken,..))&&args(token,..)")
    public void logLogin(UsernamePasswordToken token) {

        if (this.class)
            println("login:-------${token}")
    }

}

