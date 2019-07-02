package vip.hoody.api.exception.handler

import vip.hoody.api.exception.ProjectException
import vip.hoody.api.util.ResponseData
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
@Order(3)
class GlobaExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobaExceptionHandler.class)
    //默认异常处理页面
    public static final String SYS_ERROR_VIEW = "error/500";
    public static final String NON_FOUND_ERROR_VIEW = "error/404";

    /**
     * 未知异常处理
     * @param request
     * @param e
     * @param model
     * @return
     */
    @ExceptionHandler(value = ProjectException.class)
    public ResponseData handleProjectException(HttpServletRequest request, ProjectException e) {
        return new ResponseData(success: false, msg: e.getMessage())
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseData handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        log.error("发生了未知异常", e);
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        def msg = """URL:${request.getRequestURI()},\n
                     params:${request.getParameterMap().toString()},\n
                     error: ${e.getMessage()}"""
        return new ResponseData(success: false, msg: msg)
    }


    /**
     * 未知异常处理
     * @param request
     * @param e
     * @param model
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseData handleUnknownException(HttpServletRequest request, Exception e, Model model) {
        log.error("发生了未知异常", e);
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        def msg = """URL:${request.getRequestURI()},\n
                     params:${request.getParameterMap().toString()},\n
                     error: ${e.getMessage()}"""
        return new ResponseData(success: false, msg: msg)
    }

}
