package jdk_base;

import java.net.URLDecoder;

public class URLEncodeTest {

    public static void main(String[] args) throws Exception {
        String value = "https%3A%2F%2Fitem.jd.com%2F34198899041.html%3Fjd_pop%3D8e869df6-f0c3-4eb5-b2c4-bf78b204535e";

        String first = URLDecoder.decode(value, "UTF-8");
        String second = URLDecoder.decode(first, "UTF-8");

        System.out.println("the first is: " + first);
        System.out.println("the second is: " + second);


        String str[] = second.split("/");
        System.out.println("the third is: " + str[3]);

        int index = str[3].indexOf(".html");
        System.out.println("the index is: " + index);

        String skuIdStr = str[3].substring(0, index);
        System.out.println("the skuIdStr is: " + skuIdStr);

        String subStr = second.substring(20);
        System.out.println("the subStr is: " + subStr);



    }
}
