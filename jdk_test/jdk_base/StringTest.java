package jdk_base;

public class StringTest {

    public static void main(String[] args) {
        testSplit();
    }


    private static void testSplit() {
        String str = "abc~aa~~aq~~";
        String[] strList = str.split("~");
        System.out.println("the size is:  " + strList.length);
        for (String s : strList) {
            if (s == null) System.out.println("s is null");
            else if (s.equals(""))
                System.out.println("s is empty");
            else
                System.out.println("s is: " + s);
        }
    }

}
