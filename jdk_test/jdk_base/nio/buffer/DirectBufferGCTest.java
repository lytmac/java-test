package jdk_test.jdk_base.nio.buffer;

import java.nio.ByteBuffer;

public class DirectBufferGCTest {

    public static void main(String[] args) {
        for(int i = 0;i < 1000000;i++){
            //观察GC是如何处理堆外内存的
            ByteBuffer.allocateDirect(10000);
        }
    }
}
