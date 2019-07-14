package vip.hoody.api.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import vip.hoody.api.service.AliyunService
import vip.hoody.api.util.ResponseData

@RestController
@RequestMapping("dashboard")
class DashboardController {

    AliyunService aliyunService

    @Autowired
    DashboardController(AliyunService aliyunService) {
        this.aliyunService = aliyunService
    }

    @RequestMapping(value = "info/last", method = [RequestMethod.POST, RequestMethod.GET])
    @Autowired
    ResponseData lastInfo() {
        def data = aliyunService.getLast5MinData()
        return new ResponseData(data: data)
    }

}
