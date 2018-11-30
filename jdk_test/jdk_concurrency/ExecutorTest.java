package jdk_concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {

    public static void main(String[] args) {
        submitRunnableTest();
    }

    private static void submitRunnableTest() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println("the time when task commit: " + System.currentTimeMillis());

        try {

            Future<Integer> future = executor.submit(new Runnable() {
                @Override
                public void run() {
                    try { //提交的任务在休眠20s后成功返回
                        Thread.sleep(20000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 1);

            System.out.println("the result is: " + future.get());
            System.out.println("the time when task return: " + System.currentTimeMillis());

            executor.shutdown();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
