package Model;

import Controller.RakutenLinkMainController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ChenLetian on 4/5/16.
 */
public class RakutenLinkModel extends AbstractModel {

    final int sizeRow;
    final int sizeColumn;
    private int tokenCnt;

    private int[][] Matrix;//储存棋盘状态信息，-1=null

    public RakutenLinkModel(int sizeRow, int sizeColumn) {
        this.sizeRow = sizeRow;
        this.sizeColumn = sizeColumn;
    }

    /**
     * 获取行数
     *
     * @return 行数
     */
    public int getRowNumber() {
        return sizeRow;
    }

    /**
     * 获取列数
     *
     * @return 列数
     */
    public int getColumnNumber() {
        return sizeColumn;
    }

    public int getTypeOfRowAndColumn(int row, int column) {
        return Matrix[row][column];
    }

    /**
     * 初始化棋盘
     *
     * @param tc 有哪几种块
     */
    public void reset(int tc) {
        //初始化棋盘，成对储存棋盘，棋盘为满。
        tokenCnt = tc;
        Matrix = new int[sizeRow][sizeColumn];
        for (int i = 0; i < sizeRow; i++) {
            Matrix[i][0] = -1;
            Matrix[i][sizeColumn - 1] = -1;
        }
        for (int i = 0; i < sizeColumn; i++) {
            Matrix[0][i] = -1;
            Matrix[sizeRow - 1][i] = -1;
        }
        //要保证Column是偶数!
        for (int i = 1; i < sizeRow - 1; ++i)
            for (int j = 1; j < sizeColumn - 1; j += 2) {
                Matrix[i][j] = (int) (Math.random() * tokenCnt);
                Matrix[i][j + 1] = Matrix[i][j];
            }
        do {
            shuffle();
        } while (Dead());
    }

    /**
     * 判断是否为死局
     *
     * @return 是否为死局
     */
    private boolean Dead() {
        return false;
    }

    /**
     * 判断是否游戏胜利
     *
     * @return 是否游戏胜利
     */
    private boolean win() {
        for (int i = 0; i < sizeRow; ++i) for (int j = 0; j < sizeColumn; ++j) if (Matrix[i][j] != -1) return false;
        return true;
    }

    /**
     * 消除两个方块
     *
     * @param x1 第一个方块的行
     * @param y1 第一个方块的列
     * @param x2 第二个方块的行
     * @param y2 第二个方块的列
     */
    public void clearTwoBlocks(int x1, int y1, int x2, int y2) {
        Matrix[x1][y1] = -1;
        Matrix[x2][y2] = -1;
        if (win()) {
            firePropertyChange(RakutenLinkMainController.GameHasWinned, false, true);
        }
        if (Dead()) {
            shuffle();
            firePropertyChange(RakutenLinkMainController.GameHasNoBlocksToClear, false, true);
        }
    }

    /**
     * 重排棋盘
     */
    public void shuffle() {
        List<Integer> blockList = new ArrayList<>();
        for (int i = 1; i < sizeRow - 1; i++) {
            for (int j = 1; j < sizeColumn - 1; j++) {
                if (Matrix[i][j] != -1) blockList.add(j);
            }
        }
        Collections.shuffle(blockList);
        int index = 0;
        for (int i = 1; i < sizeRow - 1; i++) {
            for (int j = 1; j < sizeColumn - 1; j++) {
                if (Matrix[i][j] != -1) Matrix[i][j] = blockList.get(index++);
            }
        }
    }

    private int[] getRow(int x1, int x2, int y1, int y2) {
        int i1 = y1, j1 = y1, i2 = y2, j2 = y2;
        List<Integer> row1List = new ArrayList<>();
        List<Integer> row2List = new ArrayList<>();

        do {
            row1List.add(i1--);
        } while (i1 >= 0 && Matrix[x1][i1] == -1);
        do {
            row1List.add(j1++);
        } while (j1 < sizeColumn && Matrix[x1][j1] == -1);
        do {
            row2List.add(i2--);
        } while (i2 >= 0 && Matrix[x2][i2] == -1);
        do {
            row2List.add(j2++);
        } while (j2 < sizeColumn && Matrix[x2][j2] == -1);

        row1List.retainAll(row2List);
        Collections.sort(row1List);
        if (row1List.isEmpty()) return new int[]{};
        else return new int[]{row1List.get(0), row1List.get(row1List.size() - 1)};
    }

    private int[] getCol(int x1, int x2, int y1, int y2) {
        int i1 = x1, j1 = x1, i2 = x2, j2 = x2;
        while (i1 > 0 && Matrix[i1 - 1][y1] == -1) i1--;
        while (j1 < sizeRow - 1 && Matrix[j1 + 1][y1] == -1) j1++;
        while (i2 > 0 && Matrix[i2 - 1][y2] == -1) i2--;
        while (j2 < sizeRow - 1 && Matrix[j2 + 1][y2] == -1) j2++;

        List<Integer> col1List = new ArrayList<>();
        List<Integer> col2List = new ArrayList<>();

        do {
            col1List.add(i1--);
        } while (i1 >= 0 && Matrix[i1][y1] == -1);
        do {
            col1List.add(j1++);
        } while (j1 < sizeRow && Matrix[j1][y1] == -1);
        do {
            col2List.add(i2--);
        } while (i2 >= 0 && Matrix[i2][y2] == -1);
        do {
            col2List.add(j2++);
        } while (j2 < sizeRow && Matrix[j2][y2] == -1);

        col1List.retainAll(col2List);
        Collections.sort(col1List);
        if (col1List.isEmpty()) return new int[]{};
        else return new int[]{col1List.get(0), col1List.get(col1List.size() - 1)};


    }


    private boolean clear(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            for (int i = Math.min(y1, y2) + 1; i <= Math.max(y1, y2) - 1; ++i) if (Matrix[x1][i] != -1) return false;
        }
        if (y1 == y2) {
            for (int i = Math.min(x1, x2) + 1; i <= Math.max(x1, x2) - 1; ++i) if (Matrix[i][y1] != -1) return false;
        }
        return true;
    }

    /**
     * 判断是否可消除
     *
     * @param x1 第一个方块的行
     * @param y1 第一个方块的列
     * @param x2 第二个方块的行
     * @param y2 第二个方块的列
     * @return 是否可消除
     */
    public boolean Removeable(int x1, int y1, int x2, int y2) {//判断是否可消除
        if (!(x1==x2 && y1==y2)&&Matrix[x1][y1] == Matrix[x2][y2]) {
            int[] row = getRow(x1, x2, y1, y2);
            if (row.length > 0) {
                //System.out.printf("%d,%d\n", row[0], row[1]);
                for (int i = row[0]; i <= row[1]; ++i) {
                    if (clear(x1, i, x2, i)) return true;
                }
            }
            int[] col = getCol(x1, x2, y1, y2);
            if (col.length > 0) {
                //System.out.printf("%d,%d\n", col[0], col[1]);
                for (int i = col[0]; i <= col[1]; ++i) {
                    if (clear(i, y1, i, y2)) return true;
                }
            }
        }
        return false;
    }

    // for debug
    private void Print() {
        for (int i = 0; i < sizeRow; i++) {
            for (int j = 0; j < sizeColumn; j++) System.out.printf("%d ", Matrix[i][j]);
            System.out.println();
        }
    }

    /**
     *
     */
    void timing() {
        firePropertyChange(RakutenLinkMainController.GameTimesUp, false, true);
    }

}
