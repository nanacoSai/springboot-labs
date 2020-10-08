package cn.chx.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.interestOps();

        while (true) {
            // 通过 Selector 选择 Channel
            int readyChannels = selector.select(5000);
            if (readyChannels == 0) {
                System.out.println("no ready channel found");
                continue;
            }
            // 获得可操作的 Channel
            Set selectedKeys = selector.selectedKeys();
            // 遍历 SelectionKey 数组
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                    System.out.println("a connection was accepted by a ServerSocketChannel.");
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                    System.out.println("a connection was established with a remote server");
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                    System.out.println("a channel is ready for reading");
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                    System.out.println("a channel is ready for writing");
                }
                // 移除
                keyIterator.remove(); // <1>
            }
        }
    }
}
