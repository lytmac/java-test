package jdk_concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 校验Collections.synchronizedMap()返回的同步容器是否支持迭代。
 * Q1: 为什么System.out.println会导致reader线程无法进入？
 *
 * @author lytmac
 */
public class SynchronizedMapTest {
    public static void main(String[] args) throws Exception {
        final Map<String, String> map = new HashMap<>();
        final Map<String, String> syncMap = Collections.synchronizedMap(map);

        Thread read = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    /**
                     * 因为这里会遍历整个Map，但是又不会抢锁，所以在write线程执行put会抛出ConcurrentModificationException异常。
                     */
                    for (String key : syncMap.keySet()) {
                        System.out.println(map.get(key));
                    }
                }
            }
        }, "read");

        Thread write = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random(Integer.MAX_VALUE);
                while (true) {
                    int key = random.nextInt();
//                  System.out.println(key);
                    syncMap.put(String.valueOf(key), String.valueOf(key));
//                  System.out.println("the map size is: " + syncMap.size());
                }
            }
        }, "write");

        write.start();
        Thread.sleep(2000);
        System.out.println("---------------------------------- reader launched --------------------------------------");
        read.start();
    }
}
