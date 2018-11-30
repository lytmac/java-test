package jdk_concurrency;

import java.util.concurrent.*;

public class CpuUsageWhenThreadSleepTest {

    private static final ExecutorService executorService = new ThreadPoolExecutor(10,
            20,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(100),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        while (!executorService.isShutdown()) {
            for (int index = 0; index < 10000000; index++) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
