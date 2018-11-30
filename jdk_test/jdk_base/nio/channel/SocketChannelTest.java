package jdk_base.nio.channel;

import java.nio.channels.ServerSocketChannel;

public class SocketChannelTest {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel channel1 = ServerSocketChannel.open();
        ServerSocketChannel channel2 = ServerSocketChannel.open();

        System.out.println(channel1.hashCode());
        System.out.println(channel2.hashCode());

        if(channel1 == channel2) {
            System.out.println("unique server socket channel object");
        }

        if(channel1.equals(channel2)) {
            System.out.println("unique server socket channel object");
        }

    }
}
