package jdk_test.jdk_base;

public class ExceptionTest {

    private static boolean doTest() throws Exception {
        boolean result = true;
        try {
            result = firstInvoke();
        } catch (Exception e) {
            System.out.println("doTest, catch exception");
            result = false;
            throw e;
        } finally {
            System.out.println("doTest, finally; return value= " + result);
            return result;
        }
    }

    private static boolean firstInvoke() throws Exception {
        boolean result = true;
        try {
            result = secondInvoke();
            if (!result) {
                return false;
            }
            System.out.println("firstInvoke, at the end of try");
            return result;
        } catch (Exception e) {

            //为什么这一行没打出来？
            System.out.println("firstInvoke, catch exception");
            result = false;
            throw e;
        } finally {
            System.out.println("firstInvoke, finally; return value= " + result);
            return result;
        }
    }

    private static boolean secondInvoke() throws Exception {
        boolean result = true;
        try {
            int dividend = 12;

            for (int index = 2; index >= -2; index--) {
                int calculate = dividend / index;
                System.out.println("index = " + index + "the calculate = " + calculate);
            }
            return true;
        } catch (Exception e) {
            System.out.println("secondInvoke, catch exception!");
            result = false;
            throw e;
        } finally {
            System.out.println("secondInvoke, finally; return value = " + result);
            return result;
        }
    }

    public static void main(String[] args) {

        try {
            System.out.println(doTest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
