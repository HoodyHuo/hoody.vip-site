package vip.hoody.api.service

import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import vip.hoody.api.util.AliyunEcsUtil

@Service
class AliyunService {

    AliyunEcsUtil aliyunEcsUtil

    @Autowired
    AliyunService(AliyunEcsUtil aliyunEcsUtil) {
        this.aliyunEcsUtil = aliyunEcsUtil
    }

    //todo 缓存 1分钟一次
    def getLast5MinData() {
        long dateBefor5Min = new Date().getTime() - 5 * 60 * 1000
        HashMap<DescribeInstancesResponse.Instance, HashMap> data = aliyunEcsUtil.getDashboardDataSinceLastTime(dateBefor5Min)
        Map result = new HashMap<String, HashMap>()
        data.keySet().each { instance ->
            result.put(instance.instanceName, [
                    imageId                : instance.imageId,
                    zoneId                 : instance.zoneId,
                    instanceType           : instance.instanceType,
                    status                 : instance.status,
                    internetMaxBandwidthIn : instance.internetMaxBandwidthIn,
                    internetMaxBandwidthOut: instance.internetMaxBandwidthOut,
                    publicIpAddress        : instance.publicIpAddress,
                    items                  : data.get(instance)
            ])
        }
        return result

    }
}
