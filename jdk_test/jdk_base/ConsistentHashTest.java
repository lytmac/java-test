package jdk_base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConsistentHashTest {

    private static char[] seed = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static Random random = new Random();

    public static void main(String[] args) {
        List<String> keyList = new ArrayList<>();
        for (int index = 0; index < 1000000; index++) {
            keyList.add(genRandomStr(16));
        }

        getMatchNum(keyList, 99, 100);
    }

    /**
     * 计算以slot数量取模计算slot位置算法，在slot改变后，能继续生效的key的数量
     *
     * @param keyList
     * @param oldNodeNum
     * @param newNodeNew
     */
    private static void getMatchNum(List<String> keyList, int oldNodeNum, int newNodeNew) {
        int match = 0;

        for (String str : keyList) {
            int hashCode = hash(str);
            int oldKey = hashCode % oldNodeNum;
            int newKey = hashCode % newNodeNew;

            if (oldKey == newKey) {
                match++;
            }
        }

        System.out.println("the total num of key is: " + keyList.size());
        System.out.println("the total matched num of key is: " + match);
    }

    /**
     * hash算法，尽可能的保证生成的hash码均衡
     *
     * @param key
     * @return
     */
    private static int hash(String key) {
        int seed = 131; // 31 131 1313 13131 131313 etc..
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * seed) + key.charAt(i);
        }
        return (hash & 0x7FFFFFFF);
    }

    /**
     * 生成指定长度的字符串
     *
     * @param length 指定的长度
     * @return 生成的随机字符串
     */
    private static String genRandomStr(int length) {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < length; index++) {
            sb.append(seed[random.nextInt(seed.length)]);
        }

        return sb.toString();
    }
}
