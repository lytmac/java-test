package jdk_base;

import java.io.File;

public class FileTest {

    public static void main(String[] args) {
        testLength();
    }

    private static void testLength() {
        String filePath = "/Users/cdliuyang25/Downloads/getCateSkuListByDeviceId/attachment/device_with_cate.txt";
        File file = new File(filePath);

        System.out.println("the file length is: " + file.length());
    }
}
