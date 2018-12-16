package networking.datagram.broadcast;

import java.net.SocketException;

public class MulticastServer {
    public static void main(String[] args) {
        try {
            new MulticastServerThread().start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
