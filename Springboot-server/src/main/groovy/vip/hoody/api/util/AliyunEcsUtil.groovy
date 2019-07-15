package vip.hoody.api.util

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.IAcsClient
import com.aliyuncs.cms.model.v20190101.DescribeMetricDataRequest
import com.aliyuncs.cms.model.v20190101.DescribeMetricDataResponse
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
    Long period = 300

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
     * @param endTime
     * @return
     */
    HashMap<DescribeInstancesResponse.Instance, HashMap> getAllInstancePeriodData(Long sinceTime, Long endTime) {
        def data = new HashMap()
        try {
            DescribeInstancesResponse instancesResponse = this.getMyEcsInstances()
            instancesResponse.getInstances().each {
                DescribeInstancesResponse.Instance instance ->
                    data.put(instance, this.getDescribeMetricData(instance, sinceTime, endTime))
            }
            return data

        } catch (Exception e) {
            throw new RuntimeException("ECS 监控信息获取发生异常:${e.getMessage()}", e)
        }
    }

//    获取最近一次周期的数据
    HashMap<DescribeInstancesResponse.Instance, HashMap> getLastPeriodData(){
        Long now  = new Date().getTime()
        Long onePeriodBefore = now - (this.period*1000)
       return  this.getAllInstancePeriodData(onePeriodBefore,now)
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
                new DescribeMetricMeta("cpu_user", '当前用户CPU百分比', "%"),

                new DescribeMetricMeta("memory_usedutilization", '内存使用率', "%"),
                new DescribeMetricMeta("net_tcpconnection", 'TCP连接数', "个"),

                new DescribeMetricMeta("networkin_rate", '网卡的上行带宽', "%"),
                new DescribeMetricMeta("networkout_rate", '网卡的下行带宽', "%"),

                new DescribeMetricMeta("InternetInRate", '公网流入带宽', "bit/s"),
                new DescribeMetricMeta("InternetOutRate", '公网流出带宽', "bit/s"),

                new DescribeMetricMeta("IntranetInRate", '私网流入带宽', "bit/s"),
                new DescribeMetricMeta("IntranetOutRate", '私网流出带宽', "bit/s"),


                new DescribeMetricMeta("DiskWriteIOPS", '系统磁盘写IOPS', "Bps"),
                new DescribeMetricMeta("DiskReadIOPS", '系统磁盘读IOPS', "Count/Second"),

                new DescribeMetricMeta("VPC_PublicIP_InternetInRate", '专有网络公网流入带宽', "bit/s"),
                new DescribeMetricMeta("VPC_PublicIP_InternetOutRate", '专有网络公网流出带宽', "bit/s"),

                new DescribeMetricMeta("diskusage_utilization", '磁盘使用率', "%"),
                new DescribeMetricMeta("load_5m", '过去5分钟的系统平均负载', "%"),


        ]
        return list
    }

    /** 获取根据实例,对每一个可以查询的数据进行查询 */
    private HashMap getDescribeMetricData(
            DescribeInstancesResponse.Instance instance,
            Long sinceTime, Long endTime) {

        Map labelAndVal = new HashMap()
        List<DescribeMetricMeta> metricMetaList = this.getDescribeMetricMetaList()
        metricMetaList.each { metricMeta ->
            labelAndVal.put(metricMeta.metricName, [
                    description: metricMeta.description,
                    unit       : metricMeta.unit,
                    metricData : this.getDescribeLastData(instance, metricMeta.metricName, sinceTime, endTime)
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
    private String getDescribeLastData(
            DescribeInstancesResponse.Instance instance, String metricName,
            Long sinceTime, Long endTime) {
        DescribeMetricDataRequest request = new DescribeMetricDataRequest();
        request.setMetricName(metricName)
        request.setNamespace(this.namespace)
        request.setPeriod(this.period.toString())
        request.setStartTime(sinceTime.toString())
        request.setEndTime(endTime.toString())
        request.setDimensions("[{instanceId:'${instance.instanceId}'}]")

        DescribeMetricDataResponse response = client.getAcsResponse(request)
        if (response.code != "200") {
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
