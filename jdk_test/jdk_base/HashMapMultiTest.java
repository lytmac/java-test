package jdk_base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HashMapMultiTest {

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static final int threadCount = 10000;

    public static void main(String[] args) {
        List<Callable<Integer>> monitorList = new ArrayList<>();
        final HashMap map = new HashMap();
        for (int i = 0; i < threadCount; i++) {
            monitorList.add(() -> {
                map.put(UUID.randomUUID().toString(), "11");
                return 1;
            });
        }

        try {
            List<Future<Integer>> futures = executorService.invokeAll(monitorList);
            System.out.println("线程大小" + futures.size());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
