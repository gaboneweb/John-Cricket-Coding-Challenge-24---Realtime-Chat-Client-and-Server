package challange.chat.Server;

import java.io.*;
import java.net.Socket;
import java.util.*;

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

    }
    @Override
    public void run() {
        try{
            String line;
            while((line = reader.readLine()) != null && socket.isConnected() && !socket.isClosed()){
                writer.println(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            close();
        }
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
