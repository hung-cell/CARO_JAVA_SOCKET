/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.*;
import Model.NguoiChoi;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class NguoiChoiDAO extends DAO{
    
    public static void main(String[] args) {
        NguoiChoiDAO nguoiChoiDAO = new NguoiChoiDAO();
        System.out.println(nguoiChoiDAO);
    }
    
    public NguoiChoi DangKy(NguoiChoi nguoiChoi) {
       
        NguoiChoi result = null;
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO nguoichoi(tenDangNhap,matKhau,email,hoTen,phoneNo) VALUES(?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nguoiChoi.getTenDangNhap());
            ps.setString(2, nguoiChoi.getMatKhau());
            ps.setString(4, nguoiChoi.getHoVaTen());
            ps.setString(3, nguoiChoi.getEmail());
            ps.setString(5, nguoiChoi.getSoDienThoai());
            ps.executeUpdate();
            
            //Lay ra id auto increment
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                nguoiChoi.setId(generatedKeys.getInt(1));
            }
           
            result = nguoiChoi;
        } catch (SQLException ex) {
            return null;
        }
        return result;
    
    
    }
    
    public NguoiChoi DangNhap(NguoiChoi nguoiChoi){
        NguoiChoi result = null;
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM nguoichoi WHERE tenDangNhap = ? AND matKhau = ? ");
            ps.setString(1, nguoiChoi.getTenDangNhap());
            ps.setString(2, nguoiChoi.getMatKhau());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                nguoiChoi.setId(rs.getInt("id"));
                nguoiChoi.setEmail(rs.getString("email"));
                nguoiChoi.setHoVaTen(rs.getString("hoTen"));
                nguoiChoi.setSoDienThoai(rs.getString("phoneNo"));
            }
            result = nguoiChoi;
        } catch (SQLException ex) {
            Logger.getLogger(NguoiChoiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
