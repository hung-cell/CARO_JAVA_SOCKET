/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicGame;

/**
 *
 * @author HP
 */
public class LichSuNuocDi {
    int row;
    int column;
    String tenDangNhap;

    public LichSuNuocDi(int row, int column, String tenDangNhap) {
        this.row = row;
        this.column = column;
        this.tenDangNhap = tenDangNhap;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }
    
}
