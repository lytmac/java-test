package jdk_test.jdk_base;

public class ErrorTest {

    public static void main(String[] args) {
        testOutOfMemoryError();
        testStackOverflowError();
        testException();
    }

    public static void testException() {
        try {
            int result = 1 / 0;
        } catch (Exception exception) {
            System.out.println("====================exception=======================");
            throw exception;
        } finally {
            //Exception重新抛出后，finally仍会执行
            System.out.println("==================exception finally==================");
        }

        System.out.println("=======================continue==========================");
    }

    private static void testOutOfMemoryError() {
        try {
            int[] intArray = new int[100 * 1024 * 1024];
        } catch (Error error) {
            //这里会打印出来，说明Error也是可以被捕获的
            System.out.println("====================out of memory========================");
            throw error;
        } finally {
            //Error重新抛出后，finally无法执行
            System.out.println("================out of memory finally====================");
        }

        System.out.println("=======================continue==============================");
    }

    private static void testStackOverflowError() {
        try {
            doNothing();
        } catch (Error error) {
            //这里会打印出来，说明Error也是可以被捕获的
            System.out.println("====================stack overflow========================");
            error.printStackTrace();
            throw error;
        } finally {
            System.out.println("================stack overflow finally====================");
        }

        System.out.println("=======================continue===============================");
    }

    private static void doNothing() {
        doNothing();
    }
}
