package sword_offer;

public class MaxHeapSort {


    private static boolean insert(int[] result, int num, int index) {
        //先赋值再调整

        if (index >= result.length) {
            return false;
        }

        result[index] = num;

        //从子树节点向根节点逐级调整

        while (index > 0) {
            int parent = (index - 1) / 2;
            if (result[index] > result[parent]) { //当前节点大于根节点，需要调整。
                swap(result, index, parent);
                index = (index - 1) / 2;
            } else {
                break;
            }
        }

        return true;
    }

    private static void adjust(int[] result) { //从根节点开始，向下遍历调整
        int root = 0;


        while (root < result.length) {
            int left = 2 * root + 1;
            int right = 2 * root + 2;

            //根节点和左右子节点对比，根节点与最大的子节点交换。然后继续向下遍历
            int largest = root;
            if (left < result.length && result[root] < result[left]) { //根节点小于左子树，需要调整
                largest = left;
            }
            if (right < result.length && result[largest] < result[right]) {
                largest = right;
            }

            if (largest != root) { //root需要调整
                swap(result, largest, root);
                root = largest;
            } else {
                break;
            }
        }
    }

    private static void swap(int[] result, int index, int parent) {
        int temp = result[index];
        result[index] = result[parent];
        result[parent] = temp;
    }

    private static int[] getMinKNum(int[] array, int k) {
        if (array == null || array.length <= 0) {
            return null;
        }

        int[] result = new int[k];

        for (int index = 0; index < k; index++) {
            if (!insert(result, array[index], index)) {
                return null;
            }
        }

        for (int index = k; index < array.length; index++) {
            if (array[index] < result[0]) { //待比较的节点大于大根堆的根节点，则需要插入并调整
                result[0] = array[index];
                adjust(result);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] num = new int[]{12, 32, 19, 81, 123, 321, 45, 54, 66, 64, 67, 81, 15};

        int[] min = getMinKNum(num, 7);

        for (int index = 0; index < min.length; index++) {
            System.out.print(min[index] + "\t");
        }
    }
}