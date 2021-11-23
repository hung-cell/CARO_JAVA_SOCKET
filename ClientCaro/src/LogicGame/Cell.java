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
public class Cell {
    private int x;
    private int y;
    private int w;
    private int h;
    private String value;
    public static final String X_VALUE = "X";
    public static final String O_VALUE = "Y";
    public static final String EMPTy_VALUE = "";
    public Cell() {
        this.value = "";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Cell(int x, int y, int w, int h, String value ) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
