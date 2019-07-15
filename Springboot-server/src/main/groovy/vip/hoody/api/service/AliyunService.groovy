package vip.hoody.api.service

import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import vip.hoody.api.util.AliyunEcsUtil

@Service
class AliyunService {

    private static final Logger log = LoggerFactory.getLogger(AliyunService.class);
    AliyunEcsUtil aliyunEcsUtil

    @Autowired
    AliyunService(AliyunEcsUtil aliyunEcsUtil) {
        this.aliyunEcsUtil = aliyunEcsUtil
    }

    @Cacheable("AliyunLastPeriod")
    public Map  getLastPeriodData() {
        log.info("进入缓存方法getLastPeriodData()")
        HashMap<DescribeInstancesResponse.Instance, HashMap> data =
                aliyunEcsUtil.getLastPeriodData()
        return this.formatInstanceData(data)
    }

    @Cacheable("AliyunOneDay")
    public Map getOneDayData() {
        log.info("进入缓存方法getOneDayData()")
        Long now = new Date().getTime()
        Long oneDayBefore = now - (24 * 60 * 60 * 1000)
        HashMap<DescribeInstancesResponse.Instance, HashMap> data =
                aliyunEcsUtil.getAllInstancePeriodData(oneDayBefore, now)
        return this.formatInstanceData(data)
    }

    /**
     *  读取实例信息,重组消息
     * @param origin
     * @return
     */
    private Map formatInstanceData(HashMap<DescribeInstancesResponse.Instance, HashMap> origin) {
        Map result = new HashMap<String, HashMap>()
        origin.keySet().each { instance ->
            result.put(instance.instanceName, [
                    imageId                : instance.imageId,
                    zoneId                 : instance.zoneId,
                    instanceType           : instance.instanceType,
                    status                 : instance.status,
                    internetMaxBandwidthIn : instance.internetMaxBandwidthIn,
                    internetMaxBandwidthOut: instance.internetMaxBandwidthOut,
                    publicIpAddress        : instance.publicIpAddress,
                    items                  : origin.get(instance)
            ])
        }
        return result
    }
}
