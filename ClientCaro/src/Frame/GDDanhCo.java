/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import LogicGame.Cell;
import LogicGame.Point;
import Model.NguoiChoi;
import Service.ClientProcess;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author HP
 */
public class GDDanhCo extends javax.swing.JFrame {

    Board board;
    JLabel lblTime;
    JButton btnStart;
    NguoiChoi nguoiChoi;
    ClientProcess clientProcess;

    public GDDanhCo(NguoiChoi nguoiChoi, ClientProcess clientProcess) throws IOException {
        this.clientProcess = clientProcess;
        this.nguoiChoi = nguoiChoi;
        board = new Board(clientProcess);
        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);
        board.setPreferredSize(new Dimension(600, 600));
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

    public void updateUI(Point point) {
       board.updateTrangThai(point);
    }

    public Board getBoard() {
        return board;
    }
}

class Board extends JPanel {

    private static final int N = 13;
    private static final int M = 13;
    ClientProcess clientProcess;
    String currentState = Cell.X_VALUE;
 
    private Cell matrix[][] = new Cell[N][M];

  
    public Board( ClientProcess clientProcess) throws IOException {
        //khoi tao matrix
        this.initMatrix();
        
        clientProcess.listenToDoiThu();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();

            
                // tính toán xem x,y rơi vào ô nào trong board
                for(int i=0;i<N;i++){
                    for(int j=0;j<M;j++){
                        Cell cell = matrix[i][j];
                        int cellXStart = cell.getX();
                        int cellYStart = cell.getY();

                        int cellXEnd = cellXStart + cell.getW();
                        int cellYEnd = cellYStart + cell.getH();
                        
                        // xác định được vị trí của ô đã đánh
                        if(x >= cellXStart && x <= cellXEnd && y >= cellYStart && y <=cellYEnd){
                           // gửi vị trí của ô đấy cho server
                           
                           Point point = new Point(i,j);
                           // Viết hàm clientProcess gửi vị trí ô đã click cho server
                           
                           boolean flag = clientProcess.danhCo(point);
                           
                           // server sẽ gửi lại trạng thái có được phép update screan hay không
                           if(flag){
                               System.out.println("Hang " + i + " Cot " + j +" "+ currentState); 
                               currentState = currentState.equals(Cell.O_VALUE) ? Cell.X_VALUE : Cell.O_VALUE;
                           }
                           
                           // Viết hàm clientProcess nhận thông báo từ server
                           // Khi đối thủ đánh thì nó sẽ tự động repaint lại app
                        }
                     }
                }
            }
        });

      
    }

    private void initMatrix(){
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                Cell cell = new Cell();
                matrix[i][j] = cell;
            }
        }
    }



    @Override
    public void paint(Graphics g) {
        int w = getWidth()/N;
        int h = getHeight()/M;
        System.out.println(w + " " + h);
        Graphics2D graphics2D = (Graphics2D) g;
        int k = 0;

        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                int x=  j*w;
                int y = i*h;
                System.out.println(x + " " + y);
                Cell cell = matrix[i][j];
                cell.setX(x);
                cell.setY(y);
                cell.setW(w);
                cell.setH(h);

                Color color = k %2 == 0 ? Color.white : Color.gray;
                graphics2D.setColor(color);
                graphics2D.fillRect(x,y,w,h);

                
//                Image img = k%2 ==0 ? imgX : imgO;
//                graphics2D.drawImage(img,x,y,w,h,this);
                k++;
            }
        }

    }


    void updateTrangThai(Point point) {
        currentState = currentState.equals(Cell.O_VALUE) ? Cell.X_VALUE : Cell.O_VALUE;
       System.out.println("Hang " + point.row + " Cot " + point.col +" "+ currentState); 
    }
}
