package jdk_base;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HashMapTest {

    public static void main(String[] args) {
        //创建HashMap集合：
        Map<String, String> map = new HashMap<>();
        System.out.println("HashMap元素大小:" + map.size()); //初始化HashMap.size() = 0,看来并没有预分配任何空间。

        System.out.println("=========================================================================================");

        //元素添加:
        map.put("one", "one");
        map.put("two", "two");
        map.put("three", "three");
        map.put("four", "four");
        map.put("five", "five");

        //keySet遍历集合
        for (String key : map.keySet()) {
            System.out.println("map的key是:" + key);
            System.out.println("map的value是:" + map.get(key));
        }

        System.out.println("=========================================================================================");

        //迭代器遍历集合
        Set<Map.Entry<String, String>> mapSet1 = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = mapSet1.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> mapEntry = iterator.next();
            System.out.println("map的key是:" + mapEntry.getKey());
            System.out.println("map的value是:" + mapEntry.getValue());
        }

        System.out.println("=========================================================================================");

        //for循环entrySet遍历集合
        Set<Map.Entry<String, String>> mapSet2 = map.entrySet();
        for (Map.Entry<String, String> mapEntry : mapSet2) {
            System.out.println("map的key是:" + mapEntry.getKey());
            System.out.println("map的value是:" + mapEntry.getValue());
        }

        System.out.println("=========================================================================================");

        //元素获取：通过key获取value
        String keyValue = map.get("six"); //key不存在则value返回null
        System.out.println("HashMap的key对应的value:" + keyValue);

        System.out.println("=========================================================================================");

        //元素替换：map没有提供直接set方法，而是使用新增来完成更新操作
        map.put("six", "six");
        System.out.println("HashMap的key对应的value:" + map.get("six"));

        System.out.println("=========================================================================================");

        //元素删除：
        String value = map.remove("five");
        System.out.println("HashMap集合中被删除元素的value" + value);
        //清空所有元素：
        map.clear();

        System.out.println("=========================================================================================");

        //hashMap是否包含某个key：
        boolean isContain = map.containsKey("two");
        //hashMap是否为空：
        boolean isEmpty = map.isEmpty();
        System.out.println("is contain: " + isContain);
        System.out.println("is empty: " + isEmpty);
    }
}
