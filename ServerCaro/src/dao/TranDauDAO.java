/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Model.TranDau;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 *
 * @author HP
 */
public class TranDauDAO extends DAO{
    public TranDau taoPhong(TranDau tranDau, int idNguoiChoi) throws SQLException{
        TranDau result = null;
        try{
            PreparedStatement ps = con.prepareStatement("INSERT INTO trandau(code) VALUES(?)",Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tranDau.getCode());
            ps.executeUpdate();
            
            ResultSet generatedKey = ps.getGeneratedKeys();
              if(generatedKey.next()){
                tranDau.setId(generatedKey.getInt(1));
            }
            result = tranDau;

        } catch(Exception ex){
            
        }
        return result;
    }
    public TranDau vaoPhong(TranDau tranDau, int idNguoiChoi) throws SQLException{
        TranDau result = null;
        try{
            PreparedStatement ps = con.prepareStatement("SELECT id FROM trandau WHERE code = ?");
            ps.setString(1, tranDau.getCode());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                
                tranDau.setId(rs.getInt("id"));
                System.out.println("");
            }
           
              result = tranDau;
             
        } catch(Exception ex){
            ex.getStackTrace();
        }
        return result;
    }
    
}
