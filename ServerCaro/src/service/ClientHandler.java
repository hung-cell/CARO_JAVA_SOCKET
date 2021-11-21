/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Model.NguoiChoi;
import Model.TranDau;
import dao.NguoiChoiDAO;
import dao.TranDauDAO;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class ClientHandler implements Runnable{
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public ClientHandler(Socket socket) throws IOException{
        this.socket = socket;
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.dis = new DataInputStream(socket.getInputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
       while(true){
           try {
               String request = dis.readUTF();
               System.out.println(request);
               switch(request){
                   case "dang ky":
                       NguoiChoi nguoiChoiDangKy = (NguoiChoi) ois.readObject();
                       
                       NguoiChoiDAO nguoiChoiDAO = new NguoiChoiDAO();
                       System.out.println("tao duoc nguoiChoiDAO");
                       // Nếu mà đăng ký thành công
                       NguoiChoi nguoiChoi = nguoiChoiDAO.DangKy(nguoiChoiDangKy);
                       if(nguoiChoi != null){
                           dos.writeUTF("Đăng ký thành công");
                           dos.flush();
                           oos.writeObject(nguoiChoi);
                           oos.flush();
                       }else{
                           dos.writeUTF("that bai");
                       }
                       break;
                   case "dang nhap":
                       NguoiChoi nguoiChoiDangNhap = (NguoiChoi) ois.readObject();
                       NguoiChoiDAO nguoiChoiDAO1 = new NguoiChoiDAO();
                       NguoiChoi result = nguoiChoiDAO1.DangNhap(nguoiChoiDangNhap);
                       if(result != null){
                           dos.writeUTF("thanh cong");
                           dos.flush();
                           oos.writeObject(result);
                           oos.flush();
                       }else{
                           dos.writeUTF("that bai");
                       }
                       break;
                   case "tao phong":
                       TranDau tranDauTaoPhong = (TranDau) ois.readObject();
                       int idNguoiChoi = dis.readInt();
                       TranDauDAO tranDauDAO = new TranDauDAO();
                       TranDau tranDau = tranDauDAO.taoPhong(tranDauTaoPhong,idNguoiChoi);
                       if(tranDau != null){
                           dos.writeUTF("thanh cong");
                           dos.flush();
                           oos.writeObject(tranDau);
                           oos.flush();
                       }else{
                           dos.writeUTF("that bai");
                       }
                       break;
                   case "vao phong":
                       System.out.println("vao duoc method nay");
                       TranDau tranDauVaoPhong = (TranDau) ois.readObject();
                       
                       int idNguoiChoi2 = dis.readInt();
                       System.out.println(idNguoiChoi2);
                       TranDauDAO tranDauDAO2 = new TranDauDAO();
                       tranDau = tranDauDAO2.vaoPhong(tranDauVaoPhong,idNguoiChoi2);
                       if(tranDau != null){
                           dos.writeUTF("thanh cong");
                           dos.flush();
                           oos.writeObject(tranDau);
                           oos.flush();
                       }else{
                           dos.writeUTF("that bai");
                       }
                       break;
                       
               }
               
               
               
           } catch (IOException ex) {
               Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
    }
}
