package jdk_base;

public class BitShiftOperatorTest {

    public static void main(String[] args) {
        testBitShift();
    }

    /**
     * >> : 带符号右移，右移会保留符号位
     * >>>: 无符号右移，右移不会保留符号位，高位统统补0
     * << : 左移,最左边的bit位被替换掉，最右边直接补0
     * <<<: 不存在这个符号
     */
    private static void testBitShift() {
        int a = 2, b = -2;

        System.out.println(a >> 1);  // 1
        System.out.println(b >> 1);  // -1
        System.out.println(a >>> 1); // 1
        System.out.println(b >>> 1); // 2147483647：0111 1111 1111 1111 1111 1111 1111 1111

        int c = 0b01111111111111111111111111111111;
        System.out.println(c << 1); //-2
    }
}
