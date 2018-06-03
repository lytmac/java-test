package jdk_test.jdk_concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    public static void main(String[] args) throws Exception {
        final ReentrantLock lock = new ReentrantLock();

        Thread holdThread = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    int index = 0;
                    while (true) {
                        index++;
                        System.out.println("the index is: " + index);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            System.out.println("the exception is: " + e.getMessage());
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread waiteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("the tryLock return: " + lock.tryLock());
                try {
                    lock.lockInterruptibly(); //
                } catch (InterruptedException e) {
                    System.out.println("the exception in waite thread is: " + e.getMessage());
                }
            }
        });

        holdThread.start();
        Thread.sleep(5000);
        waiteThread.start();
        waiteThread.interrupt();

    }
}
