package vip.hoody.api.util

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.IAcsClient
import com.aliyuncs.cms.model.v20190101.DescribeMetricLastRequest
import com.aliyuncs.cms.model.v20190101.DescribeMetricLastResponse
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse
import com.aliyuncs.profile.DefaultProfile
import com.aliyuncs.profile.IClientProfile
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * 阿里云 JDK 工具类
 * 与 阿里云 JDK API 交互
 * @see 'https://api.aliyun.com/'
 */
@Component
class AliyunEcsUtil {

    private static final Logger log = LoggerFactory.getLogger(AliyunEcsUtil.class)

    String regionld

    /** 统计间隔秒数 */
    String period = '600'

    String namespace = "acs_ecs_dashboard"
    String accessKey

    String accessKeySecret

    IClientProfile profile

    IAcsClient client

    /**
     * 创建阿里云API工具
     * @param regionld 地域信息 eg. "cn-hangzhou"
     *  获取地域信息 @see 'https://help.aliyun.com/document_detail/40654.html?spm=a2c4g.11186623.2.13.b436350cT64FK7#concept-h4v-j5k-xdb'
     *
     * @param accessKey 您的AccessKeyId
     * @param accessKeySecret 您的AccessKeySecret
     *  创建阿里云accessKey  * @see 'https://help.aliyun.com/document_detail/53045.html?spm=a2c4g.11186623.2.28.ff4b7ad5WWvLPp#concept-53045-zh'
     */
    AliyunEcsUtil(
            @Value('${platform.aliyun.regionld}') String regionld,
            @Value('${platform.aliyun.access-key}') String accessKey,
            @Value('${platform.aliyun.access-key-secret}') String accessKeySecret) {

        this.regionld = regionld
        this.accessKey = accessKey
        this.accessKeySecret = accessKeySecret

        this.profile = DefaultProfile.getProfile(regionld, accessKey, accessKeySecret)
        this.client = new DefaultAcsClient(profile)
    }

    /**
     * 获取名下所有ECS实例的监控信息
     * @param sinceTime 信息的开始时间
     * @return
     */
    HashMap getDashboardDataSinceLastTime(Long sinceTime) {
        def data = new HashMap()
        try {
            DescribeInstancesResponse instancesResponse = this.getMyEcsInstances()
            instancesResponse.getInstances().each {
                DescribeInstancesResponse.Instance instance ->
                    data.put(instance.instanceName, [
                            imageId:instance.imageId,
                            zoneId:instance.zoneId,
                            instanceType:instance.instanceType,
                            status:instance.status,
                            internetMaxBandwidthIn:instance.internetMaxBandwidthIn,
                            internetMaxBandwidthOut:instance.internetMaxBandwidthOut,
                            publicIpAddress:instance.publicIpAddress,
                            items:this.getDescribeMetricLast(instance, sinceTime)
                    ])
            }
            return data

        } catch (Exception e) {
            throw new RuntimeException("ECS 监控信息获取发生异常:${e.getMessage()}", e)
        }
    }

    /** 获取当前 AccessKey 下的ECS实例信息 */
    private DescribeInstancesResponse getMyEcsInstances() {
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        DescribeInstancesResponse response = this.client.getAcsResponse(request);
        return response
    }

    /**
     * 查询云监控开放的时序类监控项的监控项描述。
     * @return
     */
    private List<DescribeMetricMeta> getDescribeMetricMetaList() {

        List<DescribeMetricMeta> list = [
                new DescribeMetricMeta("CPUUtilization", 'CPU百分比', "%"),
                new DescribeMetricMeta("InternetInRate", '公网流入带宽', "bit/s"),
                new DescribeMetricMeta("IntranetInRate", '私网流入带宽', "bit/s"),
                new DescribeMetricMeta("InternetOutRate", '公网流出带宽', "bit/s"),
                new DescribeMetricMeta("IntranetOutRate", '私网流出带宽', "bit/s"),
                new DescribeMetricMeta("InternetOutRate_Percent", '公网流出带宽使用率', "%"),
                new DescribeMetricMeta("DiskReadBPS", '系统磁盘总读BPS', "Bps"),
                new DescribeMetricMeta("DiskWriteBPS", '系统磁盘总写BPS', "Bps"),
                new DescribeMetricMeta("DiskReadIOPS", '系统磁盘读IOPS', "Count/Second"),
                new DescribeMetricMeta("VPC_PublicIP_InternetInRate", '专有网络公网流入带宽', "bit/s"),
                new DescribeMetricMeta("VPC_PublicIP_InternetOutRate", '专有网络公网流出带宽', "bit/s"),
                new DescribeMetricMeta("VPC_PublicIP_InternetOutRate_Percent", '专有网络公网流出带宽使用率', "%"),
        ]
        return list
    }

    /** 获取根据实例,对每一个可以查询的数据进行查询 */
    private HashMap getDescribeMetricLast(DescribeInstancesResponse.Instance instance, Long sinceTime) {

        Map labelAndVal = new HashMap()
        List<DescribeMetricMeta> metricMetaList = this.getDescribeMetricMetaList()
        metricMetaList.each { metricMeta ->
            labelAndVal.put(metricMeta.metricName, [
                    description: metricMeta.description,
                    unit       : metricMeta.unit,
                    metricData : this.getDescribeLastData(instance, metricMeta.metricName, sinceTime)
            ])
        }
        return labelAndVal
    }
    /**
     * 查询 指定实例的指定属性的值
     * @param instance
     * @param metricName
     * @param sinceTime
     * @return
     */
    private String getDescribeLastData(DescribeInstancesResponse.Instance instance, String metricName, Long sinceTime) {
        DescribeMetricLastRequest request = new DescribeMetricLastRequest();
        request.setMetricName(metricName)
        request.setNamespace(this.namespace)
        request.setPeriod(this.period)
        request.setDimensions("[{instanceId:'${instance.instanceId}'}]")

        DescribeMetricLastResponse response = client.getAcsResponse(request)
        if (!response.success) {
            println("${metricName}: ${response.message}")
        }
        return response.datapoints
    }

    private class DescribeMetricMeta {
        String metricName
        String description
        String unit

        DescribeMetricMeta(String metricName, String description, String unit) {
            this.metricName = metricName
            this.description = description
            this.unit = unit
        }
    }
}
