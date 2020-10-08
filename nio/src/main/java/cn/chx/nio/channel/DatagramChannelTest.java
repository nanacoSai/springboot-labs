package cn.chx.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelTest {
    /**
     * 通过receive()方法从DatagramChannel接收数据
     */
    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(9999));

        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();
        int bytesSent = channel.send(buf, new InetSocketAddress("localhost", 9999));
        System.out.println(bytesSent);
    }
}
