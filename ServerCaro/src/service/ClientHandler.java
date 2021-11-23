/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import LogicGame.LogicChoiGame;
import LogicGame.Point;
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
import servercaro.ServerCaro;

/**
 *
 * @author HP
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private TranDau tranDauDangChoi;
    private NguoiChoi nguoiChoiDangChoi;
    private ClientHandler doiThu;
    boolean quyenDuocDanh = false;

    // Mỗi khi người chơi khởi động một ván cờ sẽ tạo ra 1 logic chơi game
    private LogicChoiGame logicChoiGame;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.doiThu = null;
        logicChoiGame = new LogicChoiGame();
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.dis = new DataInputStream(socket.getInputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                String request = dis.readUTF();
                System.out.println(request);
                switch (request) {
                    case "dang ky":
                        NguoiChoi nguoiChoiDangKy = (NguoiChoi) ois.readObject();

                        NguoiChoiDAO nguoiChoiDAO = new NguoiChoiDAO();
                        System.out.println("tao duoc nguoiChoiDAO");
                        // Nếu mà đăng ký thành công
                        NguoiChoi nguoiChoi = nguoiChoiDAO.DangKy(nguoiChoiDangKy);
                        if (nguoiChoi != null) {
                            dos.writeUTF("Đăng ký thành công");
                            dos.flush();
                            oos.writeObject(nguoiChoi);
                            oos.flush();
                        } else {
                            dos.writeUTF("that bai");
                        }
                        break;
                    case "dang nhap":
                        NguoiChoi nguoiChoiDangNhap = (NguoiChoi) ois.readObject();
                        NguoiChoiDAO nguoiChoiDAO1 = new NguoiChoiDAO();
                        NguoiChoi result = nguoiChoiDAO1.DangNhap(nguoiChoiDangNhap);
                        if (result != null) {
                            dos.writeUTF("thanh cong");
                            dos.flush();
                            oos.writeObject(result);
                            oos.flush();
                            nguoiChoiDangChoi = result;
                            ServerCaro.listClientHandler.add(this);
                            System.out.println("Hiện nay có số người chơi đa đăng nhập vào hệ thống là: " + ServerCaro.listClientHandler.size());
                        } else {
                            dos.writeUTF("that bai");
                        }
                        break;
                    case "tao phong":
                        TranDau tranDauTaoPhong = (TranDau) ois.readObject();
                        int idNguoiChoi = dis.readInt();
                        TranDauDAO tranDauDAO = new TranDauDAO();
                        TranDau tranDau = tranDauDAO.taoPhong(tranDauTaoPhong, idNguoiChoi);
                        if (tranDau != null) {
                            setTranDauDangChoi(tranDau);
                            dos.writeUTF("thanh cong");
                            dos.flush();
                            oos.writeObject(tranDau);
                            oos.flush();
                            quyenDuocDanh = true;
                            // Khi một người chơi thứ 2 vào phòng thì chúng ta sẽ set đối thủ 2 bên
                            // Kiểm tra ClientHandler đang hoạt động có trùng thông tin idTranDau

                        } else {
                            dos.writeUTF("that bai");
                        }
                        break;
                    case "vao phong":
                        TranDau tranDauVaoPhong = (TranDau) ois.readObject();

                        int idNguoiChoi2 = dis.readInt();
                        System.out.println(idNguoiChoi2);
                        TranDauDAO tranDauDAO2 = new TranDauDAO();
                        tranDau = tranDauDAO2.vaoPhong(tranDauVaoPhong, idNguoiChoi2);
                        if (tranDau != null) {
                            setTranDauDangChoi(tranDau);
                            dos.writeUTF("thanh cong");
                            dos.flush();
                            oos.writeObject(tranDau);
                            oos.flush();
                        
                        } else {
                            dos.writeUTF("that bai");
                        }
                        break;
                    case "danh co":         
                        System.out.println(doiThu);
                        if(doiThu == null){
                                for (ClientHandler ch : ServerCaro.listClientHandler) {
                                    System.out.println(tranDauDangChoi.getId() + " " +ch.getTranDauDangChoi().getId());
                                if (nguoiChoiDangChoi.getId() != ch.nguoiChoiDangChoi.getId()&& tranDauDangChoi.getId() == ch.getTranDauDangChoi().getId()) {
                                    
                                    doiThu = ch;
                                   
                                }
                                 
                            }
                        }
                        System.out.println(this + " " + doiThu);
                        if (quyenDuocDanh) {
                            doiThu.quyenDuocDanh = true;
                            Point nuocDiFromClient = (Point) ois.readObject();
                            System.out.println("Nhận được point");
                            boolean flag = logicChoiGame.danhCo(nuocDiFromClient.row, nuocDiFromClient.col, this.nguoiChoiDangChoi.getTenDangNhap());
                            System.out.println("LỚP CLIENTHANDERL: "+ flag);
                            if (flag) {
//                                this.dos.writeUTF("thanh cong");
//                                this.dos.flush();
                                synchronized(doiThu){
                                    System.out.println("vào được synconized");
                                    doiThu.getOos().writeObject(nuocDiFromClient);
                                    doiThu.getOos().flush();
                                }
                                
                            } else {
                                this.dos.writeUTF("that bai");
                            }
                        }
                        break;
                    case "sang luot":
                        
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

    // get set
    public TranDau getTranDauDangChoi() {
        return tranDauDangChoi;
    }

    public void setTranDauDangChoi(TranDau tranDauDangChoi) {
        this.tranDauDangChoi = tranDauDangChoi;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public NguoiChoi getNguoiChoiDangChoi() {
        return nguoiChoiDangChoi;
    }

    public void setNguoiChoiDangChoi(NguoiChoi nguoiChoiDangChoi) {
        this.nguoiChoiDangChoi = nguoiChoiDangChoi;
    }

    public ClientHandler getDoiThu() {
        return doiThu;
    }

    public void setDoiThu(ClientHandler doiThu) {
        this.doiThu = doiThu;
    }
}
