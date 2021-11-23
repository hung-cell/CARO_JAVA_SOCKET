/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicGame;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class Point implements Serializable{
    final long serialVersionUID = 3L;
    public int row, col;
    
    public Point(int row, int col){
        this.row = row;
        this.col = col;
    }
}
