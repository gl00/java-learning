package networking.network_interfaces;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class ListNets {
    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netIf : Collections.list(nets)) {
                System.out.println(netIf);
//                displayInterfaceInformation(netIf);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    private static void displayInterfaceInformation(NetworkInterface netIf) {
        System.out.println("Display Name: " + netIf.getDisplayName());
        System.out.println("Name: " + netIf.getName());
        Enumeration<InetAddress> inetAddresses = netIf.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.println("InetAddress: " + inetAddress);
        }
        System.out.println();
    }
}
