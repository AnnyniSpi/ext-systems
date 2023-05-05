package edu.javacourse.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(25225, 2000);

        System.out.println("Server is started!");
        while (true){
            Socket client = socket.accept();
            new SimpleServer(client).start();
        }
    }
}

class SimpleServer extends Thread{
    private Socket client;

    public SimpleServer(Socket client){
        this.client = client;
    }

    @Override
    public void run(){
        handleRequest();
    }

    private void handleRequest(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            StringBuilder stringBuilder = new StringBuilder("Hello, ");
            String userName = reader.readLine();
            System.out.println("Server got String: " + userName);
            Thread.sleep(2000);

            stringBuilder.append(userName);
            writer.write(stringBuilder.toString());
            writer.newLine();
            writer.flush();

            reader.close();
            writer.close();

            client.close();
        } catch (Exception e){
            e.printStackTrace(System.out);
        }
    }
}