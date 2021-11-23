/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientCaro;

import Frame.DangKyFrame;
import Frame.DangNhapFrame;
import Service.ClientProcess;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author HP
 */
public class ClientCaro {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        ClientProcess clientProcess = new ClientProcess(socket);
       
        DangNhapFrame dangNhapFrame = new DangNhapFrame(clientProcess);
        
        dangNhapFrame.setVisible(true);
        
    }
}
