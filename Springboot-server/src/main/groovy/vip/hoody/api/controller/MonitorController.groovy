package vip.hoody.api.controller

import org.apache.shiro.SecurityUtils
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import vip.hoody.api.util.ResponseData
import vip.hoody.api.util.WebsocketMessage

@Controller
class MonitorController {

    @MessageMapping("/hello")
    @SendTo("/topic/helloEcho")
    public ResponseData helloEcho(WebsocketMessage message) throws Exception {

        return new ResponseData(data: SecurityUtils.getSubject().getPrincipal())
    }
}
