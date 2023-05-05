package edu.javacourse.net;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 1000; i++) {
            SimpleClient simpleClient = new SimpleClient();
            simpleClient.start();
        }
    }

}

class SimpleClient extends Thread{
    @Override
    public void run(){
        try {
            System.out.println("Started: " + LocalDateTime.now());
            Socket socket = new Socket("127.0.0.1", 25225);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String st = "Anna";
            writer.write(st);
            writer.newLine();
            writer.flush();

            String answer = reader.readLine();
            System.out.println("Client got String: " + answer);

            reader.close();
            writer.close();
            System.out.println("Finished: " + LocalDateTime.now());
        } catch (IOException e){
            e.printStackTrace(System.out);
        }
    }
}