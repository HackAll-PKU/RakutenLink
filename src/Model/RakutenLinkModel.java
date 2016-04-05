package Model;

import Controller.RakutenLinkMainController;

import static Controller.RakutenLinkMainController.*;

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
     * @return 行数
     */
    public int getRowNumber() {
        return sizeRow;
    }

    /**
     * 获取列数
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
     * @param tc 有哪几种块
     */
    public void reset(int tc){
        //初始化棋盘，成对储存棋盘，棋盘为满。
        tokenCnt = tc;
        Matrix = new int[sizeRow][sizeColumn];
        for(int i = 0; i< sizeRow; ++i)for(int j = 0; j< sizeColumn; j+=2){
            Matrix[i][j]=(int)(Math.random()*tokenCnt);
            Matrix[i][j+1]=Matrix[i][j];
        }
        do{
            shuffle();
        }while(Dead());
    }

    /**
     * 判断是否为死局
     * @return 是否为死局
     */
    boolean Dead(){
        return false;
    }

    /**
     * 判断是否游戏胜利
     * @return 是否游戏胜利
     */
    boolean win(){
        for(int i = 0; i< sizeRow; ++i)for(int j = 0; j< sizeColumn; ++j)if(Matrix[i][j]!=-1)return false;
        return true;
    }

    /**
     * 消除两个方块
     * @param x1 第一个方块的行
     * @param y1 第一个方块的列
     * @param x2 第二个方块的行
     * @param y2 第二个方块的列
     */
    public void clearTwoBlocks(int x1, int y1, int x2, int y2){
        Matrix[x1][y1]=-1;
        Matrix[x2][y2]=-1;
        if (win()){
            firePropertyChange(RakutenLinkMainController.GameHasWinned, false, true);
        }
        if (Dead()){
            shuffle();
            firePropertyChange(RakutenLinkMainController.GameHasNoBlocksToClear, false, true);
        }
    }

    private int randInt(int max){
        return (int)(Math.random()*max);
    }

    /**
     * 重排棋盘
     */
    public void shuffle(){
        for (int i=1;i<=50;i++){
            int x1=randInt(sizeRow); int y1=randInt(sizeColumn);
            int x2=randInt(sizeRow); int y2=randInt(sizeColumn);
            int tmp=Matrix[x1][y1];
            Matrix[x1][y1]=Matrix[x2][y2];
            Matrix[x2][y2]=tmp;
        }
    }

    private int[] getRow(int x1,int x2,int y1,int y2){
        int i1=y1-1,j1=y1+1,i2=y2-1,j2=y2+1;
        while(i1>=0 && Matrix[x1][i1-1]==-1)i1--;
        while(j1< sizeColumn - 1 && Matrix[x2][j1+1]==-1)j1++;
        while(i2>=0 && Matrix[x2][i2-1]==-1)i2--;
        while(j2< sizeColumn - 1 && Matrix[x2][j2+1]==-1)j2++;
        if(j1<=i2){
            if(i1>j2)return new int[]{};
            if(j1>j2)return new int[]{i1,j2};
            if(i1>j1) return new int[]{i1,i2};
            return new int[]{j1,i2};
        }else return new int[]{};

    }

    private int[] getCol(int x1,int x2,int y1,int y2){
        int i1=x1,j1=x1,i2=x2,j2=x2;
        while(i1>=0 && Matrix[i1-1][y1]==-1)i1--;
        while(j1< sizeRow - 1 && Matrix[j1+1][y1]==-1)j1++;
        while(i2>=0 && Matrix[i2-1][y2]==-1)i2--;
        while(j2< sizeRow - 1 && Matrix[j2+1][y2]==-1)j2++;
        if(j1<=i2){
            if(i1>j2)return new int[]{};
            if(j1>j2)return new int[]{i1,j2};
            if(i1>j1) return new int[]{i1,i2};
            return new int[]{j1,i2};
        }else return new int[]{};
    }


    private boolean clear(int x1,int y1,int x2,int y2){
        if(x1==x2){
            for(int i=y1;i<=y2;++i)if(Matrix[x1][i]!=-1)return false;
        }
        if(y1==y2){
            for(int i=x1;i<=x2;++i)if(Matrix[i][y1]!=-1)return false;
        }
        return true;
    }

    /**
     * 判断是否可消除
     * @param x1 第一个方块的行
     * @param y1 第一个方块的列
     * @param x2 第二个方块的行
     * @param y2 第二个方块的列
     * @return 是否可消除
     */
    public boolean Removeable(int x1,int y1,int x2,int y2){//判断是否可消除
        if (Matrix[x1][y1]==Matrix[x2][y2]){
            int[] row=getRow(x1,x2,y1,y2);
            if(row.length>0){
                for(int i=row[0];i<=row[1];++i){
                    if(clear(x1,i,x2,i))return true;
                }
            }
            int[] col=getCol(x1,x2,y1,y2);
            if(col.length>0)for(int i=col[0];i<=col[1];++i)
                if(clear(i,y1,i,y2))return true;
            //行判断
            //判断是否可消除,若可消除
        }
        return false;
    }
    // for debug
    private void Print(){
        for(int i = 0; i< sizeRow; i++){
            for(int j = 0; j< sizeColumn; j++) System.out.printf("%d ",Matrix[i][j]);
            System.out.println();
        }
    }

    /**
     *
     */
    void timing(){
        firePropertyChange(RakutenLinkMainController.GameTimesUp, false ,true);
    }

}
