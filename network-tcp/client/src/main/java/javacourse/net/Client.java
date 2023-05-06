package javacourse.net;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 8; i++) {
            SimpleClient simpleClient = new SimpleClient(i);
            simpleClient.start();
        }
    }

}

class SimpleClient extends Thread{

    private final static String[] COMMAND = {
            "HELLO", "MORNING", "DAY", "EVENING"
    };

    private int cmdNumber;

    public SimpleClient(int cmdNumber){
        this.cmdNumber = cmdNumber;
    }

    @Override
    public void run(){
        try {
//            System.out.println("Started: " + LocalDateTime.now());
            Socket socket = new Socket("127.0.0.1", 25225);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String command = COMMAND[cmdNumber % COMMAND.length];
            String st = command + " " + "Anna";
            writer.write(st);
            writer.newLine();
            writer.flush();

            String answer = reader.readLine();
            System.out.println("Client got String: " + answer);

            reader.close();
            writer.close();
//            System.out.println("Finished: " + LocalDateTime.now());
        } catch (IOException e){
            e.printStackTrace(System.out);
        }
    }
}