package jdk_test.jdk_base.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

/**
 * asCharBuffer/asIntBuffer等方法，与原始的ByteBuffer是共享存储数组的。
 * 唯一有区别的是position、limit属性。
 */
public class ByteBufferViewTest {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10).order(ByteOrder.BIG_ENDIAN);
        CharBuffer charBuffer = byteBuffer.asCharBuffer();

        byteBuffer.put(0, (byte)0);
        byteBuffer.put(1, (byte)'h');

        byteBuffer.put(2, (byte)0);
        byteBuffer.put(3, (byte)'e');

        byteBuffer.put(4, (byte)0);
        byteBuffer.put(5, (byte)'l');

        byteBuffer.put(6, (byte)0);
        byteBuffer.put(7, (byte)'l');

        byteBuffer.put(8, (byte)0);
        byteBuffer.put(9, (byte)'o');

        /**
         * 打印结果如下：1char = 2byte
         * position is:0
         * limit is:5
         * capacity is:5
         * toString is:hello
         */
        System.out.println("position is:" + charBuffer.position());
        System.out.println("limit is:" + charBuffer.limit());
        System.out.println("capacity is:" + charBuffer.capacity());
        System.out.println("toString is:" + charBuffer.toString());


    }
}
