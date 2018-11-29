package jdk_test.jdk_base;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用递归简单实现自旋锁。
 * 例如数据库分库分表生成全局唯一的ID,在一毫秒内序列号只有2位。为了保证不出现重复的ID,一毫秒内若打满了2位(0~99),则通过自旋锁等待下一秒
 */
public class SpinLockTest {

    private static final int MAX_INDEX = 9;

    private static AtomicInteger index = new AtomicInteger(0);

    private static volatile long oldTime = System.currentTimeMillis();

    private static String genIdLinear() {
        String id;
        long newTime = System.currentTimeMillis();
        if (newTime == oldTime) { //在同一个毫秒秒周期
            if (index.get() == MAX_INDEX) {
                System.out.println("在同一毫秒周期内，等待调用");
                //这个毫秒周期内已经无法再生产可用的ID了，递归调用直到下一个毫秒周期到来
                return genIdLinear();
            } else {
                id = newTime + "-" + index.addAndGet(1);
                System.out.println("the id is: " + id);

            }
        } else { //已经是新的一毫秒周期
            index = new AtomicInteger(0); //重置为0
            oldTime = newTime;
            id = newTime + "-" + index.get();
            System.out.println("the id is: " + id);

        }

        return id;
    }

    private static String genIdSync() {
        Lock lock = new ReentrantLock();
        lock.lock();

        String id;
        try {
            long newTime = System.currentTimeMillis();
            if (newTime == oldTime) { //在同一个毫秒秒周期
                if (index.get() == MAX_INDEX) {
                    System.out.println("在同一毫秒周期内，等待调用");
                    //这个毫秒周期内已经无法再生产可用的ID了，递归调用直到下一个毫秒周期到来
                    return genIdSync();
                } else {
                    id = newTime + "-" + index.addAndGet(1);
                    System.out.println("the id is: " + id);
                }
            } else { //已经是新的一毫秒周期
                index = new AtomicInteger(0); //重置为0
                oldTime = newTime;
                id = newTime + "-" + index.get();
                System.out.println("the id is: " + id);
            }
        } catch (Exception e) {
            return null;
        } finally {
            lock.unlock();
        }

        return id;
    }

    public static void main(String[] args) {
        //testSingleThread();
        testMultiThread();

    }

    private static void testSingleThread() {
        for (int index = 0; index < 100; index++) {
            genIdLinear();
        }
    }

    private static void testMultiThread() {
        for (int index = 0; index < 100; index++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    genIdSync();
                }
            }).start();
        }
    }
}

