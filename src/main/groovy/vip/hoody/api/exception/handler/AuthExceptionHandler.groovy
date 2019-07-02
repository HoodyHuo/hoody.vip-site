package vip.hoody.api.exception.handler

import vip.hoody.api.util.ResponseData
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.LockedAccountException
import org.apache.shiro.authc.UnknownAccountException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
@Order(1)
@ResponseBody
class AuthExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(AuthExceptionHandler.class);

    @ExceptionHandler
    public ResponseData unknownAccount(UnknownAccountException e, Model model) {
//        model.addAttribute("error", "用户帐号/密码不正确")
        return new ResponseData(success: false, msg: "用户帐号/密码不正确")
    }

    @ExceptionHandler
    public ResponseData incorrectCredentials(IncorrectCredentialsException e, Model model) {
        return new ResponseData(success: false, msg: "用户帐号/密码不正确")
    }

    @ExceptionHandler
    public ResponseData lockedAccountException(LockedAccountException e, Model model) {
        return new ResponseData(success: false, msg: "用户帐号被锁定")
    }

    @ExceptionHandler
    public ResponseData authenticationException(AuthenticationException e, Model model) {
        log.error("登录异常", e);
        return new ResponseData(success: false, msg: "登录异常：${e.getMessage()}")
    }
//    @ExceptionHandler(value = AuthorizationException.class)
//    public String unauthorizedException(UnauthorizedException e, Model model) {
//        model.addAttribute("error", "您不具备权限")
//        return "error/403"
//    }

}
