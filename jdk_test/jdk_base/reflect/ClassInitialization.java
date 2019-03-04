package jdk_base.reflect;

import java.util.Random;

class InitObj1 {
    static final int staticFinal = 47;
    static final int staticFinal2 = ClassInitialization.rand.nextInt(1000);
    static {
        System.out.println("Initializing InitObj1");
    }
}
class InitObj2 {
    static int staticNonFinal = 147;
    static {
        System.out.println("Initializing InitObj2");
    }
}
class InitObj3 {
    static int staticNonFinal = 74;
    static {
        System.out.println("Initializing InitObj3");
    }
}
public class ClassInitialization {
    public static Random rand = new Random(47);
    public static void main(String[] args) throws Exception {
        //没有触发static块的执行，因为xxx.class默认不是执行初始化。
        Class initObj1 = InitObj1.class;
        System.out.println("============================================");

        // 没有触发static块的执行，因为是static & final的编译器常量。
        System.out.println(InitObj1.staticFinal);
        System.out.println("--------------------------------------------");

        // 触发了static块的执行，因为虽然是static & final变量，但是需要初始化才能计算出结果。
        System.out.println(InitObj1.staticFinal2);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");

        // 触发了static块的执行，因为是static，但非final变量，需要初始化才能知道其值应该是多少。
        System.out.println(InitObj2.staticNonFinal);
        System.out.println("********************************************");

        // 触发了static块的执行，forName()默认会执行初始化。
        Class initObj3 = Class.forName("jdk_base.reflect.InitObj3");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // 没有触发static块的执行，因为前面forName()执行已经初始化了该类，这里无需再次执行初始化。
        System.out.println(InitObj3.staticNonFinal);
    }
}
