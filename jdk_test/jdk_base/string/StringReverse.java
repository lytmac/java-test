package jdk_base.string;

public class StringReverse {

    public static void main(String[] args) {

        String origin = "you are a student";
        System.out.println(reverse(origin));
        System.out.println(reverseWord(origin));
    }

    private static String reverse(String origin) {
        if(origin == null || origin.length() <= 0) {
            return null;
        }


        int length = origin.length();
        char[] reversed = new char[length];
        int end = length - 1;
        for(int index =0; index <= length /2; index++) {
            reversed[index] = origin.charAt(end - index);
            reversed[end - index] = origin.charAt(index);
        }

        return String.valueOf(reversed);
    }

    private static String reverseWord(String origin) {
        if(origin == null || origin.length() <= 0) {
            return null;
        }

        String reversed = reverse(origin);
        if(reversed == null) {
            return null;
        }

        String[] words = reversed.split(" ");
        String[] reverseWords = new String[words.length];
        for(int index = 0; index < words.length; index++) {
            reverseWords[index] = reverse(words[index]);
        }

        StringBuilder sb = new StringBuilder();
        for(int index = 0; index < reverseWords.length; index++) {
            sb.append(reverseWords[index]).append(" ");
        }

        return sb.substring(0, sb.length() -1);
    }


}
