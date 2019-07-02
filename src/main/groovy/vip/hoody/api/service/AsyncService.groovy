package vip.hoody.api.service

import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service

import java.util.concurrent.Future

@Service
class AsyncService {

    @Async
    void doNoReturn() {
        Thread.sleep(3000)
        println("无返回值异步方法doNoReturn执行结束${new Date().toLocalDateTime()}")
    }

    @Async
    public Future<String> doReturn(int index) {
        Thread.sleep(2000);
        def result = new AsyncResult<>("这个是第${index}个调用 :${new Date().toLocalDateTime()}")
        return result
    }
}
