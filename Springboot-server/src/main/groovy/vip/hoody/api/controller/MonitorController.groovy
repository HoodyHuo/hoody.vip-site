package vip.hoody.api.controller

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import vip.hoody.api.util.ResponseData
import vip.hoody.api.util.WebsocketMessage

import java.security.Principal

@Controller
class MonitorController {

    @MessageMapping("/hello")
    @SendTo("/topic/helloEcho")
    public ResponseData helloEcho(WebsocketMessage message, Principal principal) throws Exception {
        return new ResponseData(data: "hello,${principal.name}")
    }
}
