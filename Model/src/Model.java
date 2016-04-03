
public class Model {
	static final int sizeX=5;
    static final int sizeY=10;
    
    private int[][] Matrix;//储存棋盘状态信息，-1=null
	void initMatrix(){
		//初始化棋盘，成对储存棋盘，棋盘为满。
	}
	boolean Dead(){
		//判断是否为死局
	    return false;
	}
	boolean win(){
		//判断是否游戏胜利
		boolean f=true;
		for (int i=0; i<sizeX;++i){
			if (f){
			  for (int j=0;j<sizeY;++j){
				if (Matrix[i][j]!=-1){
					f=false;
					break;
				}
			  }
			}
		}
		return f;
	}
	void setMatrix(int x,int y){
		//修改棋盘状态̬
		if (win()){
			//广播“游戏胜利”
		}
		if (Dead()){
			Reset();
			//广播“死局”
		}
	};
	void Reset(){
		//重置棋盘，随机化打乱
	};
	boolean Removeable(int x1,int y1,int x2,int y2){//判断是否可消除
		if (Matrix[x1][y1]==Matrix[x2][y2]){
			//判断是否可消除
		}
		return false;
	};
	void timing(){
		//维护时间，若时间到则广播“游戏时间结束”
	}
	public static void main(String args[]){
		
	
	}
}
