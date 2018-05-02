package jdk_base;

public class ThreadJoinTest {

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("----------thread start----------");
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    System.out.println("sleep interrupted");
                }
                System.out.println("----------thread end----------");
            }
        });

        thread.start();
        System.out.println("main thread continue running!");
        /**
         * 主线程要等待thread线程执行完后才能继续执行。
         */
        thread.join();
        System.out.println("main thread return back running!");
    }
}
