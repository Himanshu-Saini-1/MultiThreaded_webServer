
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer(){
        return (clientSocket) -> {
            try ( PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(),true);){
                toClient.println("HEllo from the server");
                toClient.close();
                clientSocket.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        };
    }
    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        try {
            ServerSocket serversocket = new ServerSocket(port);
             serversocket.setSoTimeout(10000);
             System.out.println("Server is listening to port "+ port);
             while(true){
                Socket acceptedSocket= serversocket.accept();
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
                thread.start();
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
