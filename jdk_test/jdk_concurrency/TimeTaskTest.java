package jdk_test.jdk_concurrency;

import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 1. 对于时间间隔类型的调度，Timer框架会因单个任务时间过长而放弃执行任务
 * 2. 单个任务抛出异常，会导致Timer终止线程
 */
public class TimeTaskTest {

    public static void main(String[] args) throws Exception {
        test1();
        test2();
    }

    private static void test1() {
        Timer timer = new Timer();
        long now = System.currentTimeMillis();
        timer.schedule(new LongRunningTimeTask(), 1);
        System.out.println("the first time task speed: " + (System.currentTimeMillis() - now) + "ms");
        //这个任务还是会执行
        timer.schedule(new LongRunningTimeTask(), 1);
    }

    private static void test2() throws InterruptedException {
        Timer timer = new Timer();
        long now = System.currentTimeMillis();
        timer.schedule(new ThrowExceptionTimeTask(), 1);

        /**
         * 主线程没有sleep 10ms。timer是开了一个线程执行任务
         */
        System.out.println("the timer task speed main thread time: " + (System.currentTimeMillis() - now));

        SECONDS.sleep(20);

        /**
         * ThrowExceptionTimeTask抛出异常，只是把timer线程终止了，主线程还会继续执行的。
         */
        System.out.println("===========================main thread continue running==================================");

        //this task can not be called up
        timer.schedule(new ThrowExceptionTimeTask(), 1);
        SECONDS.sleep(5);
    }



    static class ThrowExceptionTimeTask extends TimerTask {
        @Override
        public void run() {
            try {
                SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("throw exception timer task is running");
            throw new RuntimeException();
        }
    }

    static class LongRunningTimeTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("long running time task");
            try {
                SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
