package challange.chat.Server;

import java.io.*;
import java.net.Socket;
import java.util.*;

import javax.imageio.IIOException;

public class ClientHandler implements Runnable{

    public static List<ClientHandler> clientList = new ArrayList<>();

    private Socket socket;

    private final BufferedReader reader;

    private final PrintStream  writer;

    private String name;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        this.writer = new PrintStream(socket.getOutputStream());
        this.name = reader.readLine();
        sendMessage("Welcome to the chat room!!");

    }
    @Override
    public void run() {
        try{
            String line;
            while((line = reader.readLine()) != null && socket.isConnected() && !socket.isClosed()){
                if(line.equalsIgnoreCase("quit")){
                    broadCastMessage(name+ " has left the chat!!!");
                    System.out.println(name+ " has left the chat!!!");
                    throw new IIOException("Client Disconnected");
                }
                broadCastMessage(name+": "+line);
            }
            System.out.println("Client Quit");
        }catch (IOException e){
            removeClient();
        }finally {
            close();
        }
    }

    private void sendMessage(String message){
        writer.println(message);
    }


    public void broadCastMessage(String message){
        for(ClientHandler clientHandler: ClientHandler.clientList){
            if(clientHandler != this){
                clientHandler.sendMessage(message);
            }
        }
    }

    private void removeClient(){
        ClientHandler.clientList.remove(this);
    }

    public void close(){
        try{
            if(reader != null){
                reader.close();
            }
            if(writer != null){
                writer.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
