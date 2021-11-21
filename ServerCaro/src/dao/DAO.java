/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author LENOVO
 */
public class DAO {
    public static Connection con;

    public DAO() {
        if(con == null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/caro_game","root","hunghung123");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
