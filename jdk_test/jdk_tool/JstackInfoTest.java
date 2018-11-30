package jdk_tool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JstackInfoTest {

    private static ExecutorService executorService = Executors.newScheduledThreadPool(10);
    private static List<Future> futures = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        for (int index = 0; index < 10; index++) {
            futures.add(executorService.submit(new Task(index)));
        }

        for (Future future : futures) {
            System.out.println("the task " + future.get() + " finished");
        }
    }

    public static class Task implements Callable {

        private int index;

        public Task(int index) {
            this.index = index;
        }

        @Override
        public Object call() throws Exception {
            Thread.sleep(100000); //留够时间抓出jstack日志
            return index;
        }


    }
}
