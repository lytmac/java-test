package jdk_test.jdk_concurrency;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier测试代码
 * test1: CyclicBarrier指定数量的线程都到达屏障后，所有等待线程会同时继续执行
 * test2: CyclicBarrier可以复用。屏障复位后，可以阻塞原设定数量的线程
 * test3: CyclicBarrier在单个线程被中断或抛出异常的情况下，会导致其他线程都抛出异常(其他线程主动检验)
 * test4: CyclicBarrier一旦受损就不再能复用了。reset方法不会重写屏障的受损状态。
 */
public class CyclicBarrierTest {

    private static volatile int count = 0; //用于校验是否所有线程都到达屏障后才继续向下进行的

    private static CyclicBarrier barrier = new CyclicBarrier(10, new BarrierTask());

    public static void main(String[] args) throws Exception {
        for (int index = 0; index < 10; index++) {
            new Thread(new ThreadTask(index), "thread-" + index).start();
        }


        Thread.sleep(10000);

        if (barrier.isBroken()) {
            //reset方法不会修改屏障损坏的状态。一旦屏障受到损坏，这个屏障就不再可用了
            barrier.reset();
        }

        System.out.println("============================================");

        //再做一次，确认CyclicBarrier是否可以复用。
        for (int index = 0; index < 10; index++) {
            new Thread(new ThreadTask(index), "thread-" + index).start();
        }
    }

    private static class ThreadTask implements Runnable {

        private int index;

        public ThreadTask(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            int oldCount = count;
            count++;

            if (this.index == 1) { //当其中某一个线程(不限定于最后一个线程)被中断，或者抛出异常。会唤醒其他所有的已被阻塞的线程
                Thread.currentThread().interrupt();
            }

            try {
                /**
                 * 最后一个线程调用signalAll()唤醒所有已阻塞线程。已阻塞线程从这里继续往下执行
                 * 如果这一组线程中某个线程抛异常或者被中断了，其他线程都会在barrier.await()中抛出异常
                 */
                barrier.await();
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " barrier await exception: " + e.fillInStackTrace());
                return;
            }

            //通过屏障后，主线程signalAll()，子线程继续执行。
            System.out.println(Thread.currentThread().getName() + " get count is:" + oldCount);

        }
    }

    /**
     * 所有线程达到屏障点后执行的任务，由最后一个到达屏障的子线程执行。
     */
    private static class BarrierTask implements Runnable {

        @Override
        public void run() {
            //最后一个达到的子线程运行了这个任务
            System.out.println(Thread.currentThread().getName() + " run the barrier task, the total count is: " + count);
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("sleep exception: " + e.fillInStackTrace());
            }
        }
    }
}
