package vip.hoody.api.controller

import vip.hoody.api.service.WOLService
import vip.hoody.api.util.ResponseData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class WOLController {

    @Autowired
    WOLService wolService

    @RequestMapping("/wol")
    public ResponseData wol(
            @RequestParam String ip,
            @RequestParam String mac,
            @RequestParam String subnetMask) {

        wolService.wakeUpDevice(ip, mac, subnetMask)
        return new ResponseData()
    }

    @RequestMapping("/main")
    String userInfo() {
        return "main"
    }


}
