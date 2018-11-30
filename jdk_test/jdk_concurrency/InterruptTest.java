package jdk_concurrency;

/**
 * 线程中断测试
 */
public class InterruptTest {

    public static void main(String[] args) {

        //test1();
        test2();
    }

    /**
     * 运行结果中，实际执行中断操作的时间是早于子线程实际运行完成时间的。这说明中断操作并没有实时生效。
     * 被中断线程若要响应其他线程的中断操作，必须要在该线程的执行过程中调用Thread.isInterrupted()/thread.interrupted()检查中断标志位。
     */
    private static void test1() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int index = 0; index < Integer.MAX_VALUE; index++) {
                    //System.out.print("the current index" + index);
                }
                System.out.println("finished at: " + System.currentTimeMillis());
            }
        });

        thread.start();
        thread.interrupt();
        System.out.println("the main thread interrupt the running thread at: " + System.currentTimeMillis());
    }

    /**
     * 没有占用CPU运行的线程是不可能给自己的中断状态置位的。会抛出InterruptedException
     * 这就是传统的JVM内置锁的缺陷，无法响应中断
     */
    private static void test2() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000); //休眠20秒
                } catch (InterruptedException e) {
                    System.out.println("the exception is: " + e.getMessage());
                }
            }
        });

        thread.start();
        thread.interrupt();
    }
}
