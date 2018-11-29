package jdk_test.jdk_base;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 要明确的是，序列化和反序列化都是针对对象进行的，因此静态变量是不会再序列化和反序列化范围内的
 * 对于部分类，例如ArrayList这类数组容器，真实的长度是内部控制的，如果不加控制，按照默认的序列化和反序列化的规则，会将还未初始化的部分序列化为null
 * 为了解决这个问题，JVM在做序列化反序列化时，会优先调用对象的writeObject/readObject方法。因为只有类自己才知道该如何序列化和反序列化对象。
 */
public class SerializableTest {

    private static final String PATH = "/Users/cdliuyang25/Code/Git/java-test/out/test/java-test/jdk_test/jdk_base/nio/test_file/serializable";

    public static void main(String[] args) throws Exception {
        //testFuncWriteObject();
        testTransient();
        //testInherit();
    }

    private static void testFuncWriteObject() throws Exception {
        File file = new File(PATH);

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));

        List<String> strList = new ArrayList<>(100);
        strList.add("a");
        strList.add("b");
        strList.add("c");
        strList.add("d");
        strList.add("e");

        outputStream.writeObject(strList);
        List<String> strNew = (ArrayList<String>) inputStream.readObject();

        int size = strNew.size();

        for (int index = 0; index < size; index++) {
            System.out.println("the member is: " + strNew.get(index));
        }
    }

    private static void testTransient() throws Exception {
        TestObj objOrigin = new TestObj(1, 2, 3);

        File file = new File(PATH);

        FileInputStream fis = new FileInputStream(file);

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));


        outputStream.writeObject(objOrigin);
        outputStream.flush();
        outputStream.close();

        if (fis.available() == 0) System.out.println("--------");
        else if (fis.available() < 0) System.out.println("++++++++++");
        else System.out.println("==========");

        while (inputStream.available() > 0) {
            TestObj objRead = (TestObj) inputStream.readObject();

            System.out.println("member 0: " + objRead.getMember0());
            System.out.println("member 1: " + objRead.getMember1());
            System.out.println("member 2: " + objRead.getMember2());
            System.out.println("member 3: " + objRead.getMember3()); //打印的是:0
        }


    }

    private static class TestObj implements Serializable {
        private static final long serialVersionUID = 1L;

        private static int member0 = 10;

        private int member1;
        private int member2;
        private transient int member3;

        public TestObj(int member1, int member2, int member3) {
            this.member1 = member1;
            this.member2 = member2;
            this.member3 = member3;
        }

        private int getMember0() {
            return member0;
        }

        private int getMember1() {
            return member1;
        }

        private int getMember2() {
            return member2;
        }

        private int getMember3() {
            return member3;
        }

    }

    private static void testInherit() throws Exception {
        File file = new File(PATH);

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));

        Child child = new Child();
        outputStream.writeObject(child);

        Child childNew = (Child) inputStream.readObject();
        System.out.println("member 1: " + childNew.getMember1());  //打印的是:0，因为父类没有实现Serializable接口
        System.out.println("member 2: " + childNew.getMember2());  //打印的是:0，因为父类没有实现Serializable接口
        System.out.println("member 3: " + childNew.getMember3());
        System.out.println("member 4: " + childNew.getMember4());

    }

    private static class Parent { //没有实现序列化接口
        private int member1;
        private int member2;

        /**
         * 子类对象反序列化时，会构造出父类对象。如果未提供构造函数会抛出InvalidClassException。
         * 因父类未实现Serializable接口，故子类对象序列化时不会记录父类对象的字段的信息
         */
        public Parent() {
        }

        public int getMember1() {
            return member1;
        }

        public void setMember1(int member1) {
            this.member1 = member1;
        }

        public int getMember2() {
            return member2;
        }

        public void setMember2(int member2) {
            this.member2 = member2;
        }
    }

    private static class Child extends Parent implements Serializable {
        private int member3;
        private int member4;

        public Child() {
            this.setMember1(1);
            this.setMember2(2);
            this.member3 = 1;
            this.member4 = 1;
        }

        public int getMember3() {
            return member3;
        }

        public int getMember4() {
            return member4;
        }
    }
}
