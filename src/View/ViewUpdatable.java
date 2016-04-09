package View;

/**
 * Created by archimekai on 16/4/1.
 * 此接口说明了更新界面时提供的函数
 * 这些函数应该由controller调用
 */
public interface ViewUpdatable {

    /**
     * 当两个方块被成功消去时调用,用于绘制消去成功时的有关动画和声音, 并且会将有关的方块设置为不可见
     *  @param rowBlock1 被消去的方块1的横坐标
     * @param columnBlock1 被消去的方块1的纵坐标
     * @param rowBlock2 被消去的方块2的横坐标
     * @param columnBlock2 被消去的方块2的纵坐标
     * @param Nodes
     */
    void didClearTwoBlocksSuccessful(int rowBlock1, int columnBlock1, int rowBlock2, int columnBlock2, int[][] Nodes);


    /**
     * 用户消去两个方块,消去不成功时调用,用于绘制消去失败时的有关动画和声音
     *
     * @param rowBlock1 被消去的方块1的横坐标
     * @param columnBlock1 被消去的方块1的纵坐标
     * @param rowBlock2 被消去的方块2的横坐标
     * @param columnBlock2 被消去的方块2的纵坐标
     */
    void didClearTwoBlocksUnsuccessful(int rowBlock1, int columnBlock1, int rowBlock2, int columnBlock2);

    /**
     * 通知view游戏剩余时间已耗尽。
     */
    void noTimeRemaining();

    /**
     * 成功完成游戏。
     */
    void didSuccess();

    /**
     * 重新加载棋盘数据
     */
    void reload();

    /**
     * 更新时间
     * @param progress 进度,以百分比记
     * @param remainingTime 剩余时间,单位为秒
     */
    void updateTime(double progress, double remainingTime);

    /**
     * 更新作弊次数
     * @param type 0为shuffle,1为hint
     * @param value 新值
     */
    void updateCheatStatus(int type, int value);

    /**
     * 询问是否准备好游戏开始
     */
    void askForReady();
}