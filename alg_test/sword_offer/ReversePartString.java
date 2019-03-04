package sword_offer;


/**
 * 将一个字符数组的前size个字符翻转到整个字符数组的后面，如"asdfghjkl",翻转前4个，即为"ghjklasdf"。
 */
public class ReversePartString {

    public static void main(String[] args) {

        char[] str = new char[]{'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l' };
        reversePart(str, 3);

        for (int index = 0; index < str.length; index++) {
            System.out.print(str[index]);
        }

        System.out.print("\n");
    }

    private static void reverse(char[] charList, int start, int end) {
        if (charList == null || charList.length <= 0 || start < 0 || end <= 0) {
            return;
        }

        for (int indexR = start, indexS = end; indexR < indexS; indexR++, indexS--) {
            char temp = charList[indexR];
            charList[indexR] = charList[indexS];
            charList[indexS] = temp;
        }
    }

    private static void reversePart(char[] charList, int size) {

        int totalLength = charList.length;

        reverse(charList, 0, size - 1);
        reverse(charList, size, totalLength - 1);
        reverse(charList, 0, totalLength - 1);

    }
}
