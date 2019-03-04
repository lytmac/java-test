package sword_offer;

public class ReverseSentence {

    private static final char INTERVAL = ' ';

    public static void main(String[] args) {
        char[] str = new char[] {'I', ' ', 'a', 'm', ' ', 'a', ' ',  's', 't', 'u', 'd', 'e', 'n', 't'};

        reverseSentence(str);

        for(int index = 0; index < str.length; index++) {
            System.out.print(str[index]);
        }
        System.out.print("\n========================");
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

    private static void reverseSentence(char[] sentence) {

        if(sentence == null | sentence.length <= 0) {
            return;
        }

        int totalLength = sentence.length;
        int start = 0, end = 0;
        while(end < totalLength) {
            if(sentence[end] != INTERVAL) {
                end++;
            } else {
                reverse(sentence, start, end - 1);
                end++;
                start = end;
            }
        }

        reverse(sentence, start, end-1);

        reverse(sentence, 0, totalLength - 1);
    }
}
