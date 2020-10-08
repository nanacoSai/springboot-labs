package cn.chx.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelTest {
    /**
     * socketChannel.close();
     * 可以设置 SocketChannel 为非阻塞模式 socketChannel.configureBlocking(false);
     * 设置之后，就可以在异步模式下调用connect(), read() 和write()了
     *
     * 如果SocketChannel在非阻塞模式下，此时调用connect()，该方法可能在连接建立之前就返回了。
     * 为了确定连接是否建立，可以调用finishConnect()的方法。像这样：
     * socketChannel.configureBlocking(false);
     * socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
     * while(! socketChannel.finishConnect() ){
     *     //wait, or do something else...
     * }
     */
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 9999));
        System.out.println("write start ...");
        long t1 = System.currentTimeMillis();
        // 注意SocketChannel.write()方法的调用是在一个while循环中的。Write()方法无法保证能写多少字节到SocketChannel。
        // 所以，我们重复调用write()直到Buffer没有要写的字节为止
        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();
        while(buf.hasRemaining()) {
            socketChannel.write(buf);
        }
        System.out.println("write end cost: " + (System.currentTimeMillis() - t1));
    }
}
