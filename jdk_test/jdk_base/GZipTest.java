package jdk_base;

import java.io.*;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipTest {


    public static void main(String[] args) {
        String str = "H4sIAAAAAAAAAK1Ru47CMBD8F7tNYa/f/AC65qrrUApDrLtIkCCTIKEo/367ETkC6ERDs9LO7Gt2Bha7LtfbvktsNbBU9bvY1W3DVkyxgvWnlNepqVJGQCIQv9M6t/3xyu/q7vJREReUwPyY23Pd7NKEARCy709T71iwbfqJ57rNtCmn/bTo63JM19kzrdlqM7CqPqTmdKvQVJFjU33GA+abciyei2hMFbuIPJMA0nOMABQ1cVdMmSCnSBdK64CDCQGrhAusHMvbLepNtyiJ4w0o7oShpdYJbr3lNtDbnHK43FkMgdqsDEgB9lgAtSwI1s28lFIrioZGzBtQnbFzi1Oe++CX+/yDQHijQB2C40aI5T2TYhoXvDFcOuH1FN3NjT+H4D+HHjB9L0G+R8L9wx4+/qRv+dDilb9Pfr42EBWO4y9lmGgcngMAAA==";
        uncompress(str);
    }

    private static byte[] compress(String value) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(value.getBytes("utf-8"));
            gzip.close();
            return out.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    private static void uncompress(String value) {
        try {
            byte[] bytes = Base64.getDecoder().decode(value);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            byte[] readBuffer = new byte[1024];
            while (gzipInputStream.read(readBuffer) != -1) {
                System.out.println("读取到的内容：");
                System.out.println(new String(readBuffer).trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
