package jdk_test.jdk_concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    private static final String[] strArray = new String[10];
    private static Lock lock = new ReentrantLock();
    private static final Condition notEmpty = lock.newCondition();
    private static final Condition notFull = lock.newCondition();

    private static int count = 0;
    private static int putIndex = 0;
    private static int takeIndex = 0;

    public static void main(String[] args) throws InterruptedException {
        takeFromArrayMultiThread();
        putToArrayMultiThread();

    }

    private static void putToArrayMultiThread() throws InterruptedException {
        for (int index = 0; index < 100000; index++) {
            lock.lockInterruptibly();
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (count == strArray.length) {
                            try {
                                notFull.await();
                                System.out.println("put thread " + Thread.currentThread() + "await at not Full");
                            } catch (InterruptedException e) {
                                System.out.println("the exception is: " + e.getMessage());
                            }
                        }

                        strArray[putIndex] = "test condition";
                        putIndex = (putIndex + 1) % strArray.length;
                        count++;
                        notEmpty.signal();
                        System.out.println("the current count is: " + count);
                        System.out.println("the current put index is: " + putIndex);
                    }
                }, "thread-" + index).start();
            } finally {
                lock.unlock();
            }
        }
    }

    private static void takeFromArrayMultiThread() throws InterruptedException {
        for (int index = 0; index < 100000; index++) {
            lock.lockInterruptibly();
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (count == 0) {
                            try {
                                notEmpty.await();
                                System.out.println("put thread " + Thread.currentThread() + "await at not empty");
                            } catch (InterruptedException e) {
                                System.out.println("the exception is: " + e.getMessage());
                            }
                        }

                        strArray[takeIndex] = "";
                        takeIndex = (takeIndex - 1) % strArray.length;
                        count++;
                        notFull.signal();
                        System.out.println("the current count is: " + count);
                        System.out.println("the current put index is: " + takeIndex);
                    }
                }, "thread-" + index).start();
            } finally {
                lock.unlock();
            }
        }
    }
}
