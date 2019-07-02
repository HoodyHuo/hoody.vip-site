package vip.hoody.api.service


import org.springframework.stereotype.Service
import vip.hoody.api.util.WolUtil

@Service
class WOLService {
    /**
     *  网络MagicPackage 唤醒设备
     * @param ip
     * @param mac
     * @param subnetMask
     * @return
     */
    def wakeUpDevice(String ip, String mac, String subnetMask) {
        WolUtil.wakeUpDevice(ip, mac, subnetMask)
    }
}
