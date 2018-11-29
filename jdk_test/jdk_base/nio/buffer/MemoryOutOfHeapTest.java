package jdk_test.jdk_base.nio.buffer;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * 使用对外内存的途径只有两个：使用未公开的Unsafe和NIO包下ByteBuffer。
 */
public class MemoryOutOfHeapTest {

    public static void main(String[] args) {
        //testWithByteBuffer();
        testWithUnsafe();

    }

    private static void testWithUnsafe() {
        try {
            // 通过反射获取rt.jar下的Unsafe类
            Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            // return (Unsafe) theUnsafeInstance.get(null);是等价的
            theUnsafeInstance.get(Unsafe.class);

            Unsafe unsafe = (Unsafe) theUnsafeInstance.get(Unsafe.class);

            for (int index = 0; index < 100000000; index++) {
                long pointer = unsafe.allocateMemory(10 * 1024 * 1024);

                /**
                 * 如果不释放内存,运行一段时间会报错java.lang.OutOfMemoryError
                 */
                // unsafe.freeMemory(pointer);

                if (index % 10000 == 0) System.out.println("the index is: " + index);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JVM参数: -verbose:gc -XX:+PrintGCDetails -XX:+DisableExplicitGC -XX:MaxDirectMemorySize=40M
     *
     * DisableExplicitGC默认为false, 可以看到运行过程中GC会频繁自动进行。说明采用该方式，JVM或者用户代码是会对堆外内存进行回收的。
     * DisableExplicitGC设置为true, 会导致OOM(Direct buffer memory)，说明堆外内存的回收依赖于System.gc()。
     *
     */
    private static void testWithByteBuffer() {
        for (int index = 0; index < 1000000; index++) {
            ByteBuffer.allocateDirect(10 * 1024 * 1024);
        }
    }
}
