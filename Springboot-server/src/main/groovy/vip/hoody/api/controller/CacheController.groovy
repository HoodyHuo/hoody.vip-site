package vip.hoody.api.controller

import vip.hoody.api.service.CacheService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CacheController {

    @Autowired
    CacheService cacheService

    @RequestMapping("/cache")
    @ResponseBody
    List cache() {
        return cacheService.getList()

    }
}
