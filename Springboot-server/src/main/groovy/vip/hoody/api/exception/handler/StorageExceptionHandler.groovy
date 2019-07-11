package vip.hoody.api.exception.handler

import vip.hoody.api.exception.StorageFileNotFoundException
import vip.hoody.api.util.ResponseData
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@ControllerAdvice
@Order(1)
class StorageExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(StorageExceptionHandler.class);

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
