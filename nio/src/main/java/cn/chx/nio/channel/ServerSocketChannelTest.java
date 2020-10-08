package cn.chx.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelTest {

    /**
     * 通过调用ServerSocketChannel.close() 方法来关闭ServerSocketChannel
     * accept()方法会一直阻塞到有新连接到达
     * ServerSocketChannel可以设置成非阻塞模式 serverSocketChannel.configureBlocking(false);
     */
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            //do something with socketChannel...
            if (socketChannel != null) {
                System.out.println(socketChannel);

                ByteBuffer buf = ByteBuffer.allocate(48);
                int bytesRead = socketChannel.read(buf);
                while (bytesRead != -1) {
                    System.out.println("Read " + bytesRead);
                    buf.flip();
                    while(buf.hasRemaining()){
                        System.out.print((char) buf.get());
                    }
                    System.out.println();
                    buf.clear();
                    if (!socketChannel.finishConnect()) {
                        bytesRead = socketChannel.read(buf);
                    } else {
                        bytesRead = -1;
                    }

                }

            }
        }
    }
}
