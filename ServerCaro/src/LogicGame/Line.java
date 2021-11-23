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
public class Line {
    final long serialVersionUID = 4L;
    public int row1, row2, col1, col2;

    public Line(int row1, int row2, int col1, int col2) {
        this.row1 = row1;
        this.row2 = row2;
        this.col1 = col1;
        this.col2 = col2;
    }
    
    public Line(Point p1, Point p2) {
        this.row1 = p1.row;
        this.col1 = p1.col;
        this.row2 = p2.row;
        this.col2 = p2.col;
    }
}
