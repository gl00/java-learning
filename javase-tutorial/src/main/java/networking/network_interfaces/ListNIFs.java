package networking.network_interfaces;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class ListNIFs {
    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            ArrayList<NetworkInterface> list = Collections.list(nets);
            System.out.println(list.size());
            for (NetworkInterface netIf : list) {
                System.out.println("Display name: " + netIf.getDisplayName());
                System.out.println("Name: " + netIf.getName());
                displaySubInterfaces(netIf);
                System.out.println();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    private static void displaySubInterfaces(NetworkInterface netIf) {
        Enumeration<NetworkInterface> subIfs = netIf.getSubInterfaces();
        for (NetworkInterface subIf : Collections.list(subIfs)) {
            System.out.println("\tSub Interface Display name: " + subIf.getDisplayName());
            System.out.println("\tSub Interface Name: " + subIf.getName());
        }
    }
}
