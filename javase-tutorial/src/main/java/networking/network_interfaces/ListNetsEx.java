package networking.network_interfaces;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class ListNetsEx {
    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                displayInterfaceInformation(netint);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        System.out.println("Display Name: " + netint.getDisplayName());
        System.out.println("Name: " + netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.println("InetAddress: " + inetAddress);
        }
        System.out.println("Up? " + netint.isUp());
        System.out.println("Loopback? " + netint.isLoopback());
        System.out.println("PointToPoint? " + netint.isPointToPoint());
        System.out.println("Supports multicast? " + netint.supportsMulticast());
        System.out.println("Virtual? " + netint.isVirtual());
        System.out.println("Hardware address: " + parseMac(netint.getHardwareAddress()));
        System.out.println("MTU: " + netint.getMTU());

        System.out.println();
    }

    /**
     * 返回硬件地址 hardwareAddress 的十六进制字符串表示。
     *
     * @param hardwareAddress
     * @return 十六进制字符串表示的硬件地址，例如：BA-57-47-50-AD-EC。如果 hardwareAddress 为 null，则返回字符串 null。
     */
    private static String parseMac(byte[] hardwareAddress) {
        if (hardwareAddress == null) {
            return "null";
        }
        StringBuilder mac = new StringBuilder();
        for (byte b : hardwareAddress) {
            // Java 中的 byte 为有符号整数，取值范围是[-128, 127]
            // 如果直接用 b 和 256 相加，会因为超出 byte 的取值范围而报错。
            // 注意：
            //   如果是写成 b = b + 256; 编译器报错不兼容的类型
            //   如果是写成 b += 256; 不会报错，执行的时候直接跳过此语句。这应该是 IDEA 的 bug? Eclipse 也有此问题。
            // 所以需要使用 int 类型计算
            int tmp = b;
            if (b < 0) {
                tmp += 256;
            }
            mac.append(Integer.toHexString(tmp).toUpperCase()).append("-");
        }
        mac.deleteCharAt(mac.length() - 1);
        return mac.toString();
    }
}