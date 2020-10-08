package cn.chx.nio.buffer;

import java.nio.ByteBuffer;

public class BufferTest {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(48);
        ByteBuffer buf3 = ByteBuffer.allocateDirect(48);
    }
}
