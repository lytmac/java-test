package jdk_base.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteOrderTest {

    public static void main(String[] args) {
        test1();
    }

    /**
     * ByteBuffer默认是大端字节顺序，调用order(ByteOrder)方法可以将其改为指定的字节顺序。
     * ByteBuffer指定的字节顺序将影响读写操作
     * printf info: 结果都体现出了右移24位的操作，实际上是第四字节转移到第一字节，第三字节转移到第二字节，第二字节转移到第三字节，第四字节转移到第一字节
     * the int num is: 16777216 --> 1 < 24
     * the int num is: 33554432 --> 2 < 24
     * the int num is: 50331648 --> 3 < 24
     * the int num is: 67108864 --> 4 < 24
     * the int num is: 83886080 --> 5 < 24
     */
    private static void test1() {
        //默认是大端字节顺序
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);


        byteBuffer.putInt(1).putInt(2).putInt(3).putInt(4).putInt(5);

        //调整byteBuffer的字节顺序为小端字节顺序
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            System.out.println("the int num is: " + byteBuffer.getInt());
        }


    }
}
