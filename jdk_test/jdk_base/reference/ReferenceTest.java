package jdk_base.reference;


import java.lang.ref.*;
import java.util.HashMap;
import java.util.Map;

public class ReferenceTest {

    public static void main(String[] args) {
        //testWeak();
        testSoft();
        //testPhantom();
    }


    /**
     * 设置JVM参数: -XX:+PrintGCDetails. 方便观察GC日志
     * 在GC开始之后，将weakReference压入ReferenceQueue之前，weakReference.referent被置位NULL,
     */
    private static void testWeak() {
        Object obj = new Object();

        final ReferenceQueue<Object> weakQueue = new ReferenceQueue<>();
        final WeakReference<Object> weakReference = new WeakReference<>(obj, weakQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Reference<Object> queueReference = (Reference<Object>) weakQueue.poll();
                    //GC开始时，weakReference.referent就被置位NULL了
                    System.out.println("the weak referent is: " + weakReference.get());
                    if (queueReference != null) {
                        System.out.println("the reference has already been send to queue");
                        //从ReferenceQueue中拿到的weakReference.referent也一定是置位NULL了的(本质上就是同一个从ReferenceQueue中拿到的weakReference)
                        System.out.println("the queue reference is: " + queueReference.get());
                        break;
                    }
                }
            }
        }).start();

        obj = null;
        for (int index = 0; index < 100; index++) { //确保触发GC，obj即会被回收
            byte[] bytes = new byte[10 * 1024 * 1024];
        }

    }

    private static void testSoft() {
        Object obj = new Object();

        final ReferenceQueue<Object> softQueue = new ReferenceQueue<>();
        final SoftReference<Object> softReference = new SoftReference<>(obj, softQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (softReference.get() == null) { //已被回收
                        System.out.println("============================================");
                    }

                    Reference<Object> queueReference = (Reference<Object>) softQueue.poll();
                    if (queueReference != null) {
                        System.out.println("the reference has already been send to queue");
                        //从ReferenceQueue中拿到的weakReference.referent也一定是置位NULL了的(本质上就是同一个从ReferenceQueue中拿到的weakReference)
                        System.out.println("the queue reference is: " + queueReference.get());
                        break;
                    }
                }
            }
        }).start();

        obj = null;

        Map<String, Byte[]> map = new HashMap<>();
        for (int index = 0; index < 100; index++) { //触发GC了，但是obj没有被回收
            map.put(String.valueOf(index), new Byte[10 * 1024 * 1024]);
        }
    }

    private static void testPhantom() {
        Object obj = new Object();

        final ReferenceQueue<Object> phantomQueue = new ReferenceQueue<>();
        final PhantomReference<Object> phantomReference = new PhantomReference<>(obj, phantomQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Reference<Object> reference = (Reference<Object>) phantomQueue.poll();
                    System.out.println("the referent is: " + phantomReference.get());
                    if (reference != null) {
                        System.out.println("the reference has already been send to queue");
                        break;
                    }
                }
            }
        }).start();

        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        obj = null;
        while (true) { //确保触发GC
            byte[] bytes = new byte[10 * 1024 * 1024];
        }
    }


}
