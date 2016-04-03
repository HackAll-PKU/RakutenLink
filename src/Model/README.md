# Model的解释
请把Model你觉得需要的解释写在这里
# model涉及的接口：
1.sizeX，sizeY：棋盘的格子数目；
2.void initMatrix():初始化棋盘状态和图像信息；
3.void setMatrix(int x,int y):修改棋盘状态；同时判断win游戏胜利（）和dead死局（）
4.void Reset（）:重置棋局，随机化打乱；
5.boolean Removeable(int x1,int y1,int x2,int y2):判断两块是否可消除