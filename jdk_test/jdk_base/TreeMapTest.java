package jdk_test.jdk_base;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapTest {

    public static void main(String[] args) {
        Map<Integer, String> map = new TreeMap<>();

        map.put(7, "政治");
        map.put(1, "语文");
        map.put(4, "生物");
        map.put(5, "化学");
        map.put(9, "历史");
        map.put(3, "英语");
        map.put(8, "地理");
        map.put(2, "数学");
        map.put(6, "物理");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "---" + entry.getValue());
        }
    }
}
