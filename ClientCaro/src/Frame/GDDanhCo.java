/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import Model.NguoiChoi;
import Service.ClientProcess;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author HP
 */
public class GDDanhCo extends javax.swing.JFrame{
   
    Board board;
    JLabel lblTime;
    JButton btnStart;
    NguoiChoi nguoiChoi;
    ClientProcess clientProcess;
    
   
    public GDDanhCo(NguoiChoi nguoiChoi,ClientProcess clientProcess){
        this.clientProcess = clientProcess;
        this.nguoiChoi = nguoiChoi;
        board = new Board();
        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel,BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);
         board.setPreferredSize(new Dimension(600,600));
         JPanel bottomPanel = new JPanel();
         FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
        bottomPanel.setLayout(flowLayout);
        // tao component cho bottom panel
        btnStart = new JButton("Start");

        lblTime = new JLabel("0:0");
        bottomPanel.add(lblTime);
        bottomPanel.add(btnStart);
        jPanel.add(board);
        jPanel.add(bottomPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(jPanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}


class Board extends JPanel{
    private static final int N = 13;
    private static final int M = 13;
    
    @Override
    public void paint(Graphics g){
       int w = getWidth()/N;
       int h = getHeight()/M;
       Graphics2D graphics2D = (Graphics2D) g;
       int k = 0;
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                int x=  j*w;
                int y = i*h;
                Color color = k %2 == 0 ? Color.white : Color.gray;
                graphics2D.setColor(color);
                graphics2D.fillRect(x,y,w,h);
                k++;
            }
        }
    }
   
}