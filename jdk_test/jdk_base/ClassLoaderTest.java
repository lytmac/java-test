package jdk_test.jdk_base;

public class ClassLoaderTest {

    public static void main(String[] args) {
        System.out.println(ClassLoaderTest.class.getClassLoader());
        System.out.println(Object.class.getClassLoader());
    }



}
