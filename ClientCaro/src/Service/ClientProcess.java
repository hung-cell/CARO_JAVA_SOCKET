/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Model.NguoiChoi;
import Model.TranDau;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author HP
 */
public class ClientProcess {
    
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    public ClientProcess(Socket socket) {
          try {
            this.socket = socket;
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.dis = new DataInputStream(socket.getInputStream());
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public String dangKy(NguoiChoi nguoiChoi) throws IOException, ClassNotFoundException{
        String message = "that bai";
        System.out.println(message);
        // gửi thông điệp yêu cầu đăng ký tới server
        dos.writeUTF("dang ky");
        dos.flush();
        // gửi object NguoiChoi cho server
        oos.writeObject(nguoiChoi);
        
        String responseFromServer = dis.readUTF();
        if(responseFromServer.equals("that bai")){
             return message;
        }
        // Gửi lại đối tượng người chơi
        NguoiChoi nguoiChoiFromServer = (NguoiChoi) ois.readObject();
        if(nguoiChoiFromServer != null) message = "thanh cong";
        return message;
    }
    
    public NguoiChoi dangNhap(NguoiChoi nguoiChoi) throws IOException, ClassNotFoundException{
        NguoiChoi result = null;
        dos.writeUTF("dang nhap");
        dos.flush();
        oos.writeObject(nguoiChoi);
        
        String responseFromServer = dis.readUTF();
        if(responseFromServer.equals("thanh cong")){
            NguoiChoi nguoiChoiFromServer = (NguoiChoi) ois.readObject();
            result = nguoiChoiFromServer;
        }
        return result;
            
    }
    
    public TranDau taoTranDau(TranDau tranDau, int idNguoiChoi) {
        TranDau result = null;
        try{
            dos.writeUTF("tao phong");
            dos.flush();
            oos.writeObject(tranDau);
            dos.writeInt(idNguoiChoi);
            String responseFromServer = dis.readUTF();
            if(responseFromServer.equals("thanh cong")){
                TranDau tranDauFromServer = (TranDau) ois.readObject();
                result = tranDauFromServer;
            }
        }catch(Exception ex){
            
        }
        return result;
    }
    
    public TranDau vaoTranDau(TranDau tranDau,int idNguoiChoi){
        TranDau result = null;
        try{
            dos.writeUTF("vao phong");
            System.out.println("ok");
            dos.flush();
            oos.writeObject(tranDau);
            dos.writeInt(idNguoiChoi);
            String responseFromServer = dis.readUTF();
            System.out.println(responseFromServer + " server");
            if(responseFromServer.equals("thanh cong")){
                TranDau tranDauFromServer = (TranDau) ois.readObject();
                result = tranDauFromServer;
            }
        }catch(Exception ex){
            
        }
        return result;
    }
}
