package jdk_base;

import java.net.URLDecoder;

public class URLEncodeTest {

    public static void main(String[] args) throws Exception {
        String value = "https%3A%2F%2Fcart.jd.com%2FaddToCart.html%3Frcd%3D1%26pid%3D11564539474%26pc%3D2%26eb%3D1%26rid%3D1545275600500%26em%3D\thttps://item.jd.com/11564539474.html";

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
