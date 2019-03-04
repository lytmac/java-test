package sword_offer;

public class CountBinaryOne {


    public static void main(String[] args) {
        System.out.println(countBinaryOne(123432432));
    }

    private static int countBinaryOne(int number) {
        int count = 0;

        while(number > 0) {
            if((number & 1) == 1)
                count += 1;
            number >>= 1;
        }

        return count;
    }
}
