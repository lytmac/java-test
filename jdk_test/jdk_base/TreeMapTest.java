package jdk_test.jdk_base;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapTest {

    public static void main(String[] args) {
        Map<Integer, String> map = new TreeMap<>();

        map.put(7, "政治");
        map.put(1, "语文");
        map.put(4, "生物");
        map.put(5, "化学");
        map.put(9, "历史");
        map.put(3, "英语");
        map.put(8, "地理");
        map.put(2, "数学");
        map.put(6, "物理");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "---" + entry.getValue());
        }
    }

    /**
     * ArithmeticException extends RuntimeException
     * 继承自RuntimeException的属于非检查异常，
     */
    public static class ExceptionTest {


        public static void main(String[] args) {
            try {
                testFillInStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static void testFillInStackTrace() throws RuntimeException {
            try {
                func();
            } catch (RuntimeException e) {

                /**
                 * fillInStackTrace()是手动出发native方法，重新填充stackTrace字段。
                 * 意义在于重新抛出一个异常会保留该异常的自原抛出点的全部栈帧信息，调用fillInStackTrace()只会保留自新抛出点的全部栈帧信息
                 */
                e.fillInStackTrace();
                throw e;
            }
        }

        private static void func() throws RuntimeException {
            throw new RuntimeException("throw a runtime exception");
        }
    }
}
