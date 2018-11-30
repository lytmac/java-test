package jdk_base;



public class FinalFieldTest {

    private final int x;
    private int y;

    private static FinalFieldTest fft;

    private FinalFieldTest() {
        x = 3;
        y = 4;
    }

    private static void write() {
        fft = new FinalFieldTest();
    }

    private static void read() {
        if (fft != null) {
            //对于final字段x,是可以保证一定会看到x=3的，但是对于非final字段y,是无法保证一定会看到y=4的。
            if (fft.x == 3 && fft.y != 4) {
                System.out.println("x: " + fft.x + "---y: " + fft.y);
            }

            fft = null; //恢复为null,确保不影响下一轮测试
        } else {
            //System.out.println("fft is still not initial");
        }
    }


    public static void main(String[] args) {
        for (int index = 0; index < 10000000; index++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FinalFieldTest.read();
                }
            }).start();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    FinalFieldTest.write();
                }
            }).start();
        }

    }

}
