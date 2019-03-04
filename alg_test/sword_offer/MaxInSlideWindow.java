package sword_offer;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 取一个数组中所有长度为size的滑动窗口的最大值
 * 例如：一个数组[12, 32, 43, 25, 71, 27, 84],
 * 长度为3的滑动窗口即为[(12, 32, 43), 25, 71, 27, 84],[12, (32, 43, 25), 71, 27, 84],[12, 32, (43, 25, 71), 27, 84]等5个。
 * <p>
 * 解题思路：滑动窗口，每次只需要向后移一位。
 * 因此只需要知道后移的这一位的的元素num是否比前一个滑动窗口的最大值还大，如果是则新窗口的最大值即为num，否则还是前一个滑动窗口的最大值。
 */
public class MaxInSlideWindow {

    private static ArrayList<Integer> getMaxByList(int[] numArray, int size) {

        ArrayList<Integer> result = new ArrayList<>();
        LinkedList<Integer> deque = new LinkedList<>(); //用于存放单个滑动窗口的最大值

        if (numArray == null || numArray.length <= 0 || size == 0)
            return result;

        for (int i = 0; i < numArray.length; i++) {
            if (!deque.isEmpty() && deque.peekFirst() <= i - size)
                deque.poll();
            while (!deque.isEmpty() && numArray[deque.peekLast()] < numArray[i])
                deque.removeLast();
            deque.offerLast(i);
            if (i + 1 >= size)
                result.add(numArray[deque.peekFirst()]);
        }
        return result;
    }

    private static int[] getMaxByArray(int[] numArray, int size) {
        if (numArray == null || numArray.length <= 0 || size <= 0) {
            return null;
        }

        int resultLength = numArray.length + 1 - size;
        int[] result = new int[resultLength];

        //先找到首个窗口的最大值
        int max = getMax(numArray, size);
        result[0] = max;
        int current = 0;

        for (int index = size; index < numArray.length; index++) {
            if (numArray[index] > result[current]) {
                current++;
                result[current] = numArray[index];
            } else {
                result[current + 1] = result[current];
                current++;
            }
        }

        return result;
    }

    private static int getMax(int[] numArray, int size) {
        int max = Integer.MIN_VALUE;

        for (int index = 0; index < size; index++) {
            if (numArray[index] > max) {
                max = numArray[index];
            }
        }

        return max;
    }

    public static void main(String[] args) {
        int[] numArray = new int[]{12, 32, 43, 25, 71, 27, 84, 43, 65, 145, 55, 200, 44, 143, 213};
        ArrayList<Integer> max = getMaxByList(numArray, 3);

        for (Integer num : max) {
            System.out.print(num + "\t");
        }

        System.out.println("\n===================================================================");
        int[] max1 = getMaxByArray(numArray, 3);

        for (Integer num : max1) {
            System.out.print(num + "\t");
        }
    }
}
