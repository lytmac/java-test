package jdk_base.concurrence;

import java.util.concurrent.*;

public class ThreadPoolExecutorExtensionTest {

    private static final ExecutorService exe = new ThreadPoolExecutorExtension();

    public static void main(String[] args) {
        exe.submit((Runnable) () -> {
            throw new StackOverflowError();
        });
    }

    public static class ThreadPoolExecutorExtension extends ThreadPoolExecutor {

        public ThreadPoolExecutorExtension() {
            super(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>());
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("========before execute========");
        }


        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println("========after execute==========");
        }
    }

}
