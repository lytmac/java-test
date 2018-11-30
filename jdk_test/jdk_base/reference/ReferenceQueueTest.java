package jdk_base.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class ReferenceQueueTest {

    private static ReferenceQueue<byte[]> referenceQueue = new ReferenceQueue<>();
    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) {
        //storeInHashMapWithStrongReference();
        //storeInHashMapWithWeakReference();
        storeInWeakHashMapWithWeakReference();
    }

    /**
     * 因为map会持有字节数组对象，所以GC无法回收字节数组对象
     */
    private static void storeInHashMapWithStrongReference() {
        Object value = new Object();
        Map<Object, Object> map = new HashMap<>();

        for (int i = 0; i < 10000; i++) {
            byte[] bytes = new byte[_1M]; //bytes是一个强引用，但是for循环体结束后即只有weakReference一个引用了。

            map.put(bytes, value);
        }

        System.out.println("map.size->" + map.size());
    }

    private static void storeInHashMapWithWeakReference() {
        Object value = new Object();
        Map<Object, Object> map = new HashMap<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int cnt = 0;
                    WeakReference<byte[]> weakReference;
                    while ((weakReference = (WeakReference) referenceQueue.remove()) != null) {
                        /**
                         * weakReference 并没有被回收，回收的是weakReference所持有的referent引用(weakReference.get() == null).
                         */
                        System.out.println((cnt++) + " 回收了: " + weakReference);
                        System.out.println("the weak reference is: " + weakReference + ". the referent is: " + weakReference.get());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        for (int i = 0; i < 10000; i++) {
            byte[] bytes = new byte[_1M]; //bytes是一个强引用，但是for循环体结束后即只有weakReference一个引用了。
            WeakReference<byte[]> weakReference = new WeakReference<>(bytes, referenceQueue);
            map.put(weakReference, value);
        }

        System.out.println("map.size->" + map.size());
    }

    private static void storeInWeakHashMapWithWeakReference() {
        Object value = new Object();
        Map<Object, Object> map = new WeakHashMap<>();

        for (int i = 0; i < 10000; i++) {
            byte[] bytes = new byte[_1M]; //bytes是一个强引用，但是for循环体结束后即只有weakReference一个引用了。
            map.put(bytes, value);
        }

        //不同于HashMap的是：map.size ！= 10000。只有未被回收的entry。
        System.out.println("map.size->" + map.size());
    }

}
