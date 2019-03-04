package jdk_base.collection;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(20, 0.75f, 32);
        map.put("first", "first");
    }
}
