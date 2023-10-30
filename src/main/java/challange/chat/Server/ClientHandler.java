package challange.chat.Server;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable{

    public static List<ClientHandler> clientList = new ArrayList<>();

    private Socket socket;

    private BufferedReader reader;

    private final PrintStream  writer;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        this.writer = new PrintStream(socket.getOutputStream());

    }
    @Override
    public void run() {
        while(socket.isConnected() && !socket.isClosed()){
            try{
                String line;
                while((line = reader.readLine()) != null){
                    writer.println(line);
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                close();
            }
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
