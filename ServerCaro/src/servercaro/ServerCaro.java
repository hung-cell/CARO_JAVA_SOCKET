/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercaro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import service.ClientHandler;

/**
 *
 * @author HP
 */
public class ServerCaro {

    /**
     * @param args the command line arguments
     */
    // danh sách các client đã đăng nhập thành công vào hệ thống
    public static ArrayList<ClientHandler> listClientHandler = new ArrayList<>(); 
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        while(true){
            Socket socket = serverSocket.accept();
            System.out.println("da nhan duoc ket noi cua " + socket.getInetAddress().getHostName() );
            // Cứ 1 client kết nối tới thì server sẽ gửi cho client 1 trình handler
            // Mỗi Client sẽ chiếm 1 luồng
            ClientHandler clientHandler = new ClientHandler(socket);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
        
    }
    
}
