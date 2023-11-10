package challange.chat.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    Socket socket;

    Scanner scanner;

    private BufferedReader reader;

    private PrintStream  writer;

    private String name;

    public Client(Socket socket){
        this.socket = socket;
        this.scanner = new Scanner(System.in);
        try {
            this.reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            this.writer = new PrintStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Client(){
        this.scanner = new Scanner(System.in);
    }


    public void connect(String ipAddress, int port) {
        try {
            socket = new Socket(ipAddress, port);
            this.reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            this.writer = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            //error connecting should just throw Runtime error and fail test
            throw new RuntimeException("Error connecting to Robot Worlds server.", e);
        }
    }


    public void disconnect() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            //error connecting should just throw Runtime error and fail test
            throw new RuntimeException("Error disconnecting from chat server.", e);
        }
    }


    public void runClient(){
        name = getInput("Name: ");
        sendMessage(name);
        this.listenForMessage();
        this.sendMessages();
    }



    public String getInput(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }


    private void sendMessage(String message){
        this.writer.println(message);
    }
    

    public void sendMessages(){
        try{
            while(socket.isConnected() && !socket.isClosed()){
                String message = getInput("");
                sendMessage(message);
                if(message.equalsIgnoreCase("quit")){
                    throw new IOException("Quit from the chat");
                }
                System.out.println(name+": " + message);
                
            }
        }catch(IOException e){
            disconnect();
        }
    }
    public void listenForMessage(){
        new Thread(() ->{
            try{
                while(socket.isConnected() && !socket.isClosed()){
                    String message = getMessage();

                    if(message == null){
                        break;
                    }
                    System.out.println(message);
                }
                throw new IOException();
            }catch(IOException e){
                disconnect();

            }
            
        }).start();
    }
    public String getMessage() throws IOException{
        return this.reader.readLine();
    }


    public static void main(String[] args){
        Client client = new Client();
        client.connect("localhost", 7000);
        client.runClient();
    }
}
