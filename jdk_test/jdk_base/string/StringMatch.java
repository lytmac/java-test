package jdk_base.string;

public class StringMatch {

    public static void main(String[] args) {
        char[] src = {'a', 'b', 'c', 'd', 'a', 'c', 'd', 'a', 'a', 'h', 'f', 'a', 'c', 'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'e', 'a', 'a' };
        char[] des = {'a', 'b', 'c', 'd', 'e' };

        System.out.println(compare(src, des, 0, 0));
    }


    public static boolean compare(char[] source, char[] target, int j, int k) {
        System.out.println("比较开始下标：" + k + "  " + j);

        //这个for循环负责匹配
        for (int index = j; index < j + target.length; index++) {
            if (target[index - j] == source[index]) {
                k++; //k代表当前正向数第几个字符未匹配上
                continue;
            } else { //这一段没有匹配成功
                break;
            }
        }
        //k-j代表本次比较的次数，如果和目标字符串的长度相等，则说明每个字符都对比成功，即在源字符串中找到了目标字符串
        if (k - j == target.length) {
            System.out.println("匹配成功");
            return true;
        }


        //这一段负责移位
        k = j + target.length;
        if (k < (source.length - 1)) {
            int value = check(source[k], target);
            int step = -value;
            j = k + step; //j始终标识的是下一轮比较的source段的下标
            return compare(source, target, j, j);
        } else {
            return false;
        }
    }

    /**
     * 计算字符c的反向下标
     *
     * @param c     待匹配的字符
     * @param tempT 查找的字符数组
     * @return
     */
    public static int check(char c, char[] tempT) {
        for (int i = tempT.length - 1; i >= -1; i--) {
            if (i == -1 || tempT[i] == c) {
                return i;
            } else {
                continue;
            }
        }
        return 0;
    }
}
