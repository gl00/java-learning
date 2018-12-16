package networking.datagram;

import java.net.SocketException;

public class QuoteSever {
    public static void main(String[] args) {
        try {
            new QuoteServerThread().start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
