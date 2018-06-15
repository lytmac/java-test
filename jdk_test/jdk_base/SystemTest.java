package jdk_test.jdk_base;

import java.net.URL;

public class SystemTest {

    public static void main(String[] args) {

        /*=======java程序在启动以后，会在 ${java.io.tmpdir}/hsperfdata_${User}目录下创建一个文件，文件名即为java进程的pid========*/
        /*=================================jps、jconsole、jvisualvm等工具的数据来源就是这个文件=============================*/
        System.out.println(System.getProperty("java.io.tmpdir"));
        System.out.println("--------------------------");
        /*============================================================================================================*/


        /*==========================================AppClassLoader加载目录==============================================*/
        System.out.println(System.getProperty("java.class.path"));
        System.out.println("--------------------------");
        /*============================================================================================================*/

        /*==========================================ExtClassLoader加载目录==============================================*/
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println("--------------------------");
        /*============================================================================================================*/


        /*====================================Bootstrap ClassLoader加载目录(两个都是)====================================*/
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println("--------------------------");


        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
        /*============================================================================================================*/


    }
}
