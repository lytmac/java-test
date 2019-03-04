package jdk_base.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceTest {

    public static void main(String[] args) {
        testResultOfGet();
    }

    private static void testResultOfGet() {
        Object referent = new Object();
        final ReferenceQueue queue = new ReferenceQueue();

        PhantomReference<Object> reference = new PhantomReference<>(referent, queue);

        /**
         * phantom reference的get方法永远返回null
         */
        System.out.println("before GC, phantom reference is: " + reference.get());      // NULL
        System.out.println("before GC, phantom reference queue poll: " + queue.poll()); // NULL

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Reference<Object> polled = queue.poll();
                    if (polled != null) {
                        System.out.println("the polled reference is: " + polled.toString() + ". poll time is: " + System.currentTimeMillis());
                        return;
                    } else {
                        System.out.println("=========================================");
                    }
                }
            }
        }).start();

        try {
            Thread.sleep(20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        referent = null;
        System.gc();

    }

}
