package expire_block;

import redis.clients.jedis.JedisPool;


public class RedisExpireTest {

    private static volatile JedisPool jedisPool;

    static {
        jedisPool = new JedisPool("127.0.0.1", 6379);
    }


    public static void main(String[] args) throws Exception {
        Thread writerTask = new Thread(() -> {
            for (int index = 0; index < 100000; index++) {
                jedisPool.getResource().setex("test:" + index, 10, String.valueOf(index));
            }
        });


        Thread readerTask = new Thread(() -> {
            for (int index = 0; index < 100000; index++) {
                long start = System.currentTimeMillis();
                String key = "test:" + index;
                String value = jedisPool.getResource().get(key);
                long end = System.currentTimeMillis();
                System.out.println("the value of: " + key + " is: " + value + ", spend: " + (end - start) + "ms");
            }
        });

        writerTask.start();
        Thread.sleep(9000);
        readerTask.start();
    }
}
