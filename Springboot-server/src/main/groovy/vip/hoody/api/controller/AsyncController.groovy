package vip.hoody.api.controller

import vip.hoody.api.service.AsyncService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import java.util.concurrent.Future

@Controller
class AsyncController {

    @Autowired
    AsyncService asyncService

    @RequestMapping("/async")
    @ResponseBody
    Map asyncEg() {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>()
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<String> future = asyncService.doReturn(i);
            futures.add(future);
        }
        List<String> response = new ArrayList<>();
        for (Future future : futures) {
            String string = (String) future.get();
            response.add(string);
        }
        map.put("data", response);
        map.put("消耗时间", String.format("任务执行成功,耗时{%s}毫秒", System.currentTimeMillis() - start));
        return map
    }
}
