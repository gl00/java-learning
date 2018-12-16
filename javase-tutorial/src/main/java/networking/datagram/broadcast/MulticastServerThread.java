package networking.datagram.broadcast;

import networking.datagram.QuoteServerThread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class MulticastServerThread extends QuoteServerThread {
    private static final double FIVE_SECONDS = 5000;

    public MulticastServerThread() throws SocketException {
        super("MulticastSocketThread");
    }

    @Override
    public void run() {
        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];

                // construct quote
                String dString = null;
                if (in == null) {
                    dString = new Date().toString();
                } else {
                    dString = getNextQuote();
                }
                buf = dString.getBytes();

                // send it
                // 这里用的 IP 地址是从组播 IP 地址中任选的一个
                // 关于组播：https://baike.baidu.com/item/%E7%BB%84%E6%92%AD
                InetAddress group = InetAddress.getByName("230.0.0.1");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
                socket.send(packet);

                // sleep for a while
                try {
                    sleep((long) (Math.random() * FIVE_SECONDS));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        socket.close();
    }
}
