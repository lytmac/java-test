package sword_offer;

import java.util.ArrayList;
import java.util.LinkedList;

public class SlidWindowMax {

    public static void main(String[] args) {
        int num[] = {2, 3, 4, 2, 6, 2, 5, 1};
        System.out.println(maxInWindows(num, 3));
    }

    //因为函数形式是这样的，其实因为返回的窗口最大值的数组一共就是n-w+1,不需要arraylist
    public static ArrayList<Integer> maxInWindows(int[] num, int size) {

        ArrayList<Integer> result = new ArrayList<>();
        if (num == null || size <= 0 || size > num.length)
            return result;

        LinkedList<Integer> max = new LinkedList<>();//记录窗口

        for (int i = 0; i < num.length; i++) { //遍历整个数组

            //如果新值大于队尾的，之前的那个队尾永远不是最大了，就直接弹出来就好了
            while (!max.isEmpty() && num[max.peekLast()] <= num[i])
                max.pollLast(); //移除最后一个元素

            max.addLast(i); //在max尾部加上最大值的下标

            if (max.peekFirst() == i - size)//此时，下标首次过期，说明此时的窗口其实没有包含这个下标了
                max.pollFirst();

            if (i >= size - 1)//保证一开始的不存入，假设3 2 1，只有下标大于窗口时候才判断加入此时的对头
                result.add(num[max.peekFirst()]);
        }
        return result;
    }

}
