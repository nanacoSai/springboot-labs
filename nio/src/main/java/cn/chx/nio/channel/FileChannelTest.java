package cn.chx.nio.channel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {
    /**
     * inChannel.position(fileSize); 位置会随机，不指定
     * 可以使用FileChannel.truncate()方法截取一个文件。截取文件时，文件将中指定长度后面的部分将被删除
     * FileChannel.force()方法将通道里尚未写入磁盘的数据强制写到磁盘上
     * force()方法有一个boolean类型的参数，指明是否同时将文件元数据（权限信息等）写到磁盘上
     */
    public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        inChannel.force(true);
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);
        System.out.println("read: " + bytesRead);

        long fileSize = inChannel.size();
        System.out.println("fileSize: " + fileSize);
        inChannel.position(fileSize);

        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer bufWrite = ByteBuffer.allocate(48);
        bufWrite.clear();
        bufWrite.put(newData.getBytes());
        bufWrite.flip();
        while(bufWrite.hasRemaining()) {
            inChannel.write(bufWrite);
        }
        inChannel.close();
    }
}
