package jdk_base.reference;

public class FinalizerTest {


    public static void main(String[] args) {
        //testWithoutFinalizer();
        System.out.println("==========================");
        testWithFinalizer();
    }

    private static void testWithoutFinalizer() {
        BlockWithoutFinalizer block = new BlockWithoutFinalizer();
        block = null;
        System.gc();
    }

    private static void testWithFinalizer() {
        BlockWithFinalizer block = new BlockWithFinalizer();
        block = null;
        System.gc();
    }

    static class BlockWithoutFinalizer {

        public BlockWithoutFinalizer() {
            byte[] _200M = new byte[200 * 1024 * 1024];
        }
    }

    static class BlockWithFinalizer {
        public BlockWithFinalizer() {
            byte[] _200M = new byte[200 * 1024 * 1024];
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("invoke finalize");
        }
    }
}
