package challange.chat.Server;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (!serverSocket.isClosed() && serverSocket != null) {
                System.out.println("Listening for connections at port " + serverSocket.getLocalPort());
                Socket socket = serverSocket.accept();

                ClientHandler client = new ClientHandler(socket);

                ClientHandler.clientList.add(client);

                Thread thread = new Thread(client);

                thread.start();



            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    public void close(){
        try {
            if (serverSocket != null) {
                this.serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public  static void main(String[] args){
        try {
            ServerSocket socket = new ServerSocket(7000);
            Runnable server = new Server(socket);

            Thread thread = new Thread(server);

            thread.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
