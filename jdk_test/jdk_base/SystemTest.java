package jdk_test.jdk_base;

public class SystemTest {

    public static void main(String[] args) {

        //java程序在启动以后，会在 ${java.io.tmpdir}/hsperfdata_${User}目录下创建一个文件，文件名即为java进程的pid。
        //jps、jconsole、jvisualvm等工具的数据来源就是这个文件。
        System.out.println(System.getProperty("java.io.tmpdir"));


    }
}
