/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class TranDau implements Serializable{
    
    final long serialVersionUID = 2L;
    
    private int id;
    private String code;
    private NguoiChoi benThang;

    public TranDau() {
    }

    public TranDau(int id, String code, NguoiChoi nguoiChoi) {
        this.id = id;
        this.code = code;
        this.benThang = nguoiChoi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public NguoiChoi getNguoiChoi() {
        return benThang;
    }

    public void setNguoiChoi(NguoiChoi nguoiChoi) {
        this.benThang = nguoiChoi;
    }
    
    
    
}
