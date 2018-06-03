package jdk_test.jdk_concurrency;

import java.util.concurrent.*;

/**
 * note-1: FutureTask 在任务还未执行前，取消任务是会成功的。
 * note-2: 对已取消成功的FutureTask调用get(timeout)获取任务执行结果,会直接抛出异常CancellationException
 */
public class FutureTaskTest {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {

        for (int index = 0; index < 100; index++) {
            FutureTask task = new FutureTask(new MyTask(index));
            executorService.submit(task);

            if (index >= 90) {
                if (task.cancel(true)) { //如果任务取消成功
                    try {
                        System.out.println("cancel task success, num is: " + task.get(1000, TimeUnit.MILLISECONDS));
                    } catch (CancellationException e) { //取消任务成功后，再调用get()会抛出该异常
                        System.out.println("the task has been canceled, can't get the out result");
                    } catch (Exception e) {
                        System.out.println(e.getCause().getMessage());
                    }
                } else {
                    System.out.println("cancel task failed");
                }
            }
        }
    }

    private static class MyTask implements Callable {

        private int taskNum;

        public MyTask(int taskNum) {
            this.taskNum = taskNum;
        }

        @Override
        public Object call() throws Exception {
            Thread.sleep(200); //确保任务submit后不会马上执行
            System.out.println(Thread.currentThread() + " is running my task: " + getTaskNum());
            return getTaskNum();
        }


        public int getTaskNum() {
            return taskNum;
        }

    }
}
