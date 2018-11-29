package jdk_test.jdk_concurrency;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(10);

        for (int index = 0; index < 10; index++) {
            new Thread(new LatchTask(latch), "task" + index).start();
        }

        latch.await();
        System.out.println("main thread continue run after all thread finished!");
    }

    private static class LatchTask implements Runnable {

        public LatchTask(CountDownLatch latch) {
            this.latch = latch;
        }

        private CountDownLatch latch;

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("the task: " + Thread.currentThread() + ": finished");
            latch.countDown();
        }
    }
}
