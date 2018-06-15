package alg_test.consistent_hash;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import static alg_test.consistent_hash.HashUtil.hash;

/**
 * 一致性hash算法实现。
 * 一致性hash算法的精妙之处在于key与存储key的节点之间的关系并不是一一对应，而是通过唯一关系对应的，永远找的都是环上hash值大于该key的hash值的节点
 * Q1: 如何保证虚拟节点相互不重合，且均匀覆盖整个circle
 * @param <T>
 */
public class ConsistentHash<T> {
    private final HashUtil hashUtil;

    // 节点的复制因子,即每个真实节点有多少个虚拟节点
    private final int numberOfReplicas;

    // 虚拟节点Map,使用TreeMap是为了维持key的有序序列。能提升查找效率
    private final SortedMap<Long, T> circle = new TreeMap<Long, T>(); //存储虚拟节点的hash值到真实节点的映射

    public ConsistentHash(HashUtil hashUtil, int numberOfReplicas, Collection<T> nodes) {
        this.hashUtil = hashUtil;
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * 添加一个真实节点，以及其所有的虚拟节点
     * 对于一个实际机器节点 node, 对应 numberOfReplicas 个虚拟节点
     *
     * @param node
     */
    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {

            /**
             * 不同的虚拟节点(i不同)有不同的hash值,但都对应同一个实际机器node
             * 虚拟node一般是均衡分布在环上的,数据存储在顺时针方向的虚拟node上
             **/
            circle.put(hash(node.toString() + i), node);
        }
    }

    /**
     * 删除一个真实节点，以及其所有的虚拟节点
     *
     * @param node
     */
    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++)
            circle.remove(hash(node.toString() + i));
    }

    /*
     * 获得一个最近的顺时针节点,根据给定的key 取Hash
     * 然后再取得顺时针方向上最近的一个虚拟节点对应的实际节点
     * 再从实际节点中取得 数据
     */
    public T get(Object key) {
        if (circle.isEmpty())
            return null;
        long hash = hash((String) key);// node 用String来表示,获得node在哈希环中的hashCode

        //获取circle中第一个大于hash的key,如circle中不存在大于hash的key则从第一个key下的节点中查找
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public long getSize() {
        return circle.size();
    }

    /*
     * 查看MD5算法生成的hashCode值---表示整个哈希环中各个虚拟节点位置
     */
    public void testBalance() {
        Set<Long> sets = circle.keySet();//获得TreeMap中所有的Key
        SortedSet<Long> sortedSets = new TreeSet<Long>(sets);//将获得的Key集合排序
        for (Long hashCode : sortedSets) {
            System.out.println(hashCode);
        }

        System.out.println("----each location 's distance are follows: ----");
        /*
         * 查看用MD5算法生成的long hashCode 相邻两个hashCode的差值
         */
        Iterator<Long> it = sortedSets.iterator();
        Iterator<Long> it2 = sortedSets.iterator();
        if (it2.hasNext())
            it2.next();
        long keyPre, keyAfter;
        while (it.hasNext() && it2.hasNext()) {
            keyPre = it.next();
            keyAfter = it2.next();
            System.out.println(keyAfter - keyPre);
        }
    }

    public static void main(String[] args) {
        Set<String> nodes = new HashSet<String>();
        nodes.add("A");
        nodes.add("B");
        nodes.add("C");

        ConsistentHash<String> consistentHash = new ConsistentHash<String>(new HashUtil(), 2, nodes);
        consistentHash.add("D");

        System.out.println("hash circle size: " + consistentHash.getSize());
        System.out.println("location of each node are follows: ");
        consistentHash.testBalance();
    }

}
