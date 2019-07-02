package vip.hoody.api.service.impl

import vip.hoody.api.service.CacheService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CacheServiceImpl implements CacheService {
    /**
     * 具有相同的参数时 会直接return 之前的结果
     * cacheDemo 时缓存名称
     * 此方法仅适用于保证为给定输入（或参数）返回相同输出（结果）的方法，无论它执行多少次。
     * @return
     */
    @Override
    @Cacheable("cacheDemo")
    public List getList() {
        simulateSlowService()
        return [1, 2, 3]
    }

    private static void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
