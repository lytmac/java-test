package jdk_concurrency;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * note-1: FutureTask 在任务还未执行前，取消任务是会成功的。
 * note-2: 对已取消成功的FutureTask调用get(timeout)获取任务执行结果,会直接抛出异常CancellationException
 */
public class FutureTaskTest {
    private static ExecutorService cacheExecutor = Executors.newCachedThreadPool();

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        testCancel();
        testAwaitDone();
    }

    private static void testCancel() {
        for (int index = 0; index < 100; index++) {
            FutureTask task = new FutureTask(new TestTask(index));
            cacheExecutor.submit(task);

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

    private static void testAwaitDone() {
        lock.lock();

        FutureTask task = new FutureTask(new TestTask(1));
        cacheExecutor.submit(task);
        try {
            condition.await();
            long start = System.currentTimeMillis();
            Integer result = (Integer) task.get();
            System.out.println("the result is:" + result + "wait time is: " + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static class TestTask implements Callable {

        private int taskNum;

        public TestTask(int taskNum) {
            this.taskNum = taskNum;
        }

        @Override
        public Object call() throws Exception {
            lock.lock();
            condition.signalAll();
            lock.unlock();
            Thread.sleep(20000); //确保任务submit后不会马上执行
            System.out.println(Thread.currentThread() + " is running my task: " + getTaskNum());
            return getTaskNum();
        }


        public int getTaskNum() {
            return taskNum;
        }

    }
}
