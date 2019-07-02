package vip.hoody.api.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class WolUtil {
    public static void wakeUpDevice(String ip, String mac, String subnetMask) {
        ip = ip.trim();
        mac = mac.trim();
        subnetMask = subnetMask.trim();
        String broadcastAddress = getBroadcastAddress(ip, subnetMask);
        mac = mac.replace("-", "");
        wakeBy(broadcastAddress, mac, 389);
    }

    /**
     * 网络唤醒
     *
     * @param ip   主机ip
     * @param mac  主机mac
     * @param port 端口
     */
    private static void wakeBy(String ip, String mac, int port) {
        //构建magic魔术包
        String MagicPacage = "FFFFFFFFFFFF";
        for (int i = 0; i < 16; i++) {
            MagicPacage += mac;
        }
        byte[] MPBinary = hexStr2BinArr(MagicPacage);
        try {
            InetAddress address = InetAddress.getByName(ip);
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket packet = new DatagramPacket(MPBinary, MPBinary.length, address, port);
            //发送udp数据包到广播地址
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException("发送魔术封包失败", e);
        }
    }

    private static byte[] hexStr2BinArr(String hexString) {
        String hexStr = "0123456789ABCDEF";
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        byte high = 0;
        byte low = 0;
        for (int i = 0; i < len; i++) {
            high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
            low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
            bytes[i] = (byte) (high | low);
        }
        return bytes;
    }

    //根据子网掩码和ip得到主机的广播地址
    public static String getBroadcastAddress(String ip, String subnetMask) {
        String ipBinary = toBinary(ip);
        String subnetBinary = toBinary(subnetMask);
        String broadcastBinary = getBroadcastBinary(ipBinary, subnetBinary);
        String wholeBroadcastBinary = spiltBinary(broadcastBinary);
        return binaryToDecimal(wholeBroadcastBinary);
    }

    //二进制的ip字符串转十进制
    private static String binaryToDecimal(String wholeBroadcastBinary) {
        String[] strings = wholeBroadcastBinary.split("\\.");
        StringBuilder sb = new StringBuilder(40);
        for (int j = 0; j < strings.length; j++) {
            String s = Integer.valueOf(strings[j], 2).toString();
            sb.append(s).append(".");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    //按8位分割二进制字符串
    private static String spiltBinary(String broadcastBinary) {
        StringBuilder stringBuilder = new StringBuilder(40);
        char[] chars = broadcastBinary.toCharArray();
        int count = 0;
        for (int j = 0; j < chars.length; j++) {
            if (count == 8) {
                stringBuilder.append(".");
                count = 0;
            }
            stringBuilder.append(chars[j]);
            count++;
        }
        return stringBuilder.toString();
    }

    //得到广播地址的二进制码
    private static String getBroadcastBinary(String ipBinary, String subnetBinary) {
        int i = subnetBinary.lastIndexOf('1');
        String broadcastIPBinary = ipBinary.substring(0, i + 1);
        for (int j = broadcastIPBinary.length(); j < 32; j++) {
            broadcastIPBinary = broadcastIPBinary + "1";
        }
        return broadcastIPBinary;
    }

    //转二进制
    private static String toBinary(String content) {
        String binaryString = "";
        String[] ipSplit = content.split("\\.");
        for (String split : ipSplit) {
            String s = Integer.toBinaryString(Integer.valueOf(split));
            int length = s.length();
            for (int i = length; i < 8; i++) {
                s = "0" + s;
            }
            binaryString = binaryString + s;
        }
        return binaryString;
    }
}
