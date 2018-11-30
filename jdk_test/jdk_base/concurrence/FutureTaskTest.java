package jdk_base.concurrence;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    public static void main(String[] args) throws Exception {
        FutureTask task = new FutureTask(new Callable<Integer>() {
            @Override
            public Integer call() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 1;
            }
        });

        new Thread(task).start();

        Integer result = (Integer) task.get();
        System.out.println("the result is: " + result);


        Integer result1 = (Integer) task.get();
        System.out.println("the result is: " + result1);



    }
}
