/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicGame;

import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class LogicChoiGame {

    public static final int ROW = 17, COL = 17;

    private ArrayList<LichSuNuocDi> lichSuCacNuocDaDi;
    private LichSuNuocDi nuocDiTruocDo;
    private String[][] board;

    public LogicChoiGame() {
        board = new String[ROW][COL];
        lichSuCacNuocDaDi = new ArrayList<>();
        nuocDiTruocDo = null;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                board[i][j] = " ";
            }
        }
    }

    public void themLichSuNuocDi(int row, int col, String tenDangNhap) {
        LichSuNuocDi nuocDi = new LichSuNuocDi(row, col, tenDangNhap);
        lichSuCacNuocDaDi.add(nuocDi);
        nuocDiTruocDo = nuocDi;
    }

    public boolean danhCo(int row, int col, String tenDangNhap) {
        // nếu người này đã đi trước đó thì không cho đánh nữa
        if (nuocDiTruocDo != null && nuocDiTruocDo.getTenDangNhap().equals(tenDangNhap)) {
            return false;
        }

        // nếu vị trí đánh nằm ngoài board
        if (row < 0 && row >= ROW && col < 0 && col >= COL) {
            return false;
        }

        // nếu vị trí đó đã đánh rồi
        if (!board[row][col].equals(" ")) {
            return false;
        }

        board[row][col] = tenDangNhap;
        themLichSuNuocDi(row, col, tenDangNhap);
        return true;
    }

    public String getValueAt(int row, int col) {
        if (row >= 0 && row < ROW && col >= 0 && col < COL) {
            return board[row][col];
        }

        return " ";
    }

    public Line CheckWin(int row, int col) {
        Point currentCell = new Point(col, row);
        Point backDir, frontDir;
        Line winPath;

        // ============ check chieu ngang =============
        backDir = new Point(-1, 0);
        frontDir = new Point(1, 0);
        winPath = this.checkWinTemplate(currentCell, backDir, frontDir);
        if (winPath != null) {
            return winPath;
        }
        // ============ check chieu doc ============
        backDir = new Point(0, -1);
        frontDir = new Point(0, 1);
        winPath = this.checkWinTemplate(currentCell, backDir, frontDir);
        if (winPath != null) {
            return winPath;
        }
        // ============ check cheo trai sang phai ============
        backDir = new Point(-1, -1);
        frontDir = new Point(1, 1);
        winPath = this.checkWinTemplate(currentCell, backDir, frontDir);
        if (winPath != null) {
            return winPath;
        }
        // ============ check cheo phai sang trai ============
        backDir = new Point(1, -1);
        frontDir = new Point(-1, 1);
        winPath = this.checkWinTemplate(currentCell, backDir, frontDir);
        if (winPath != null) {
            return winPath;
        }

        return null;
    }
    
    public Line checkWinTemplate(Point currentCell, Point backDir, Point frontDir) {
        // Lấy thông tin điểm muốn kiểm tra
        String currentData = this.getValueAt(currentCell.row, currentCell.col);

        // nếu như ô mà đang kiểm tra là rỗng => out
        if (currentData.equals(" ")) {
            return null;
        }

        // đếm số lượng ô thỏa điều kiện (>= 5 ô liên tiếp là win)
        int count = 1;
        Point from, to, temp;

        // đếm về phía sau
        from = currentCell;
        while (true) {
            temp = new Point(from.row + backDir.row, from.col + backDir.col);
            String data = this.getValueAt(temp.row, temp.col);

            if (!data.equals(currentData)) {
                break;
            }
            from = temp;
            count++;
        }

        // count to front
        to = currentCell;
        while (true) {
            temp = new Point(to.row + frontDir.row, to.col + frontDir.col);
            String data = this.getValueAt(temp.row, temp.col);

            if (!data.equals(currentData)) {
                break;
            }
            to = temp;
            count++;
        }

        // nếu có 5 ô giống nhau liên tiếp nhau => win
        if (count == 5) {
            return new Line(from.row, to.row, from.col , to.col);
        }

        return null;
    }
    
    // get set

    public ArrayList<LichSuNuocDi> getLichSuCacNuocDaDi() {
        return lichSuCacNuocDaDi;
    }

    public void setLichSuCacNuocDaDi(ArrayList<LichSuNuocDi> lichSuCacNuocDaDi) {
        this.lichSuCacNuocDaDi = lichSuCacNuocDaDi;
    }

    public LichSuNuocDi getNuocDiTruocDo() {
        return nuocDiTruocDo;
    }

    public void setNuocDiTruocDo(LichSuNuocDi nuocDiTruocDo) {
        this.nuocDiTruocDo = nuocDiTruocDo;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

}
