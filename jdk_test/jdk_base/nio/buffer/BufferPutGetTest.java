package jdk_test.jdk_base.nio.buffer;

import java.nio.ByteBuffer;

public class BufferPutGetTest {

    public static void main(String[] args) {
        getAndPutTest();
    }

    private static void getAndPutTest() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(30);

        System.out.println("the position is: " + buffer.position());

        buffer.put(0, (byte) 'h'); //指定下标的put操作，不会修改buffer的position
        System.out.println("the position is: " + buffer.position());

        buffer.put((byte) 'e'); //此时position还是0.写入的e会覆盖h
        System.out.println("the position is: " + buffer.position());

        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println("the char is: " + ((char) buffer.get()));
        }

    }
}
