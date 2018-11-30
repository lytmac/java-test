package jdk_concurrency;


/**
 * 测试volatile是否能保证原子性
 */
public class VolatileTest {

    private static volatile Long volatileNum = 0L;

    private static void doAdd() {
        volatileNum++;
    }

    public static void main(String[] args) {
        for (int index = 0; index < 10; index++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        doAdd();
                        System.out.println(Thread.currentThread().getName() + " the volatile num is: " + volatileNum);
                    }

                }
            }, "thread-" + index).start();
        }
    }
}
