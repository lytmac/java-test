package jdk_test.jdk_base.nio.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOInfoTest {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8082);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new SocketHandler(socket)).start();
            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }

    }

    private static class SocketHandler implements Runnable {

        public SocketHandler(Socket socket) {
            this.socket = socket;
        }

        private Socket socket;

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                writer = new PrintWriter(this.socket.getOutputStream(), true);

                String body;
                while (true) {
                    body = reader.readLine();
                    if (body == null) {
                        break;
                    }
                    System.out.println(body);
                    writer.println(body);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) reader.close();
                    if (writer != null) writer.close();
                    if (socket != null) socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
