package jdk_base.nio;

import java.nio.*;

public class UseFloatBuffer {
    static public void main(String args[]) {

        try {
            FloatBuffer buffer = FloatBuffer.allocate(10);

            for (int i = 0; i < buffer.capacity(); ++i) {
                float f = (float) Math.sin((((float) i) / 10) * (2 * Math.PI));
                buffer.put(f);
            }

            buffer.flip();

            while (buffer.hasRemaining()) {
                float f = buffer.get();
                System.out.println(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
