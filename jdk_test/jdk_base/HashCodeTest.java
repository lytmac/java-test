package jdk_test.jdk_base;

public class HashCodeTest {

    public static void main(String[] args) {
        System.out.println(new Object().hashCode());
        System.out.println(new String("asdfghjkl").hashCode());
    }
}
