package jdk_base;

public class FinallyTest {

    public static void main(String[] args) {
        System.out.println("return value of test(): " + testReturnBeforeTry());
        System.out.println("return value of test(): " + testReturnInTryWithException());
        System.out.println("return value of test(): " + testExitBeforeFinally());
    }

    /**
     * 两个注释的代码，无论是正常的返回还是抛出异常，都没有走到try语句块中，finally语句块是不会执行的
     *
     * @return
     */
    public static int testReturnBeforeTry() {
        int i = 1;

        //if (i == 1) return 0;

        System.out.println("the previous statement of try block");
        //i = i / 0;
        try {
            System.out.println("try block");
            return i;
        } finally {
            System.out.println("finally block");
        }
    }

    /**
     * 进入try语句块中，finally语句块得到了执行。
     *
     * @return
     */
    private static int testReturnInTryWithException() {
        int i = 1;

        System.out.println("the previous statement of try block");

        try {
            i = i / 0;
            System.out.println("try block");
            return i;
        } finally {
            System.out.println("finally block");
        }
    }

    /**
     * 在finally块执行前明确的调用了System.exit(0)，finally块没有执行。
     *
     * @return
     */
    private static int testExitBeforeFinally() {
        int i = 1;

        System.out.println("the previous statement of try block");

        try {
            System.out.println("try block");
            System.exit(0);
            return i;
        } finally {
            System.out.println("finally block");
        }
    }
}
