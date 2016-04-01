package View;

/**
 * Created by archimekai on 16/4/1.
 * 此接口说明了更新界面时提供的函数
 * 这些函数应该由controller调用
 */
public interface ViewUpdatable {

    /**
     * 当两个方块被成功消去时调用,需要被消去的两个方块的横纵坐标,用于绘制消去成功时的有关动画和声音
     *
     * @param rowBlock1
     * @param columnBlock1
     * @param rowBlock2
     * @param columnBlock2
     */
    public void didClearTwoBlocksSuccessful(int rowBlock1, int columnBlock1, int rowBlock2, int columnBlock2);


    /**
     * 用户消去两个方块,消去不成功时调用,需要被消去的两个方块的横纵坐标,用于绘制消去失败时的有关动画和声音
     *
     * @param rowBlock1
     * @param columnBlock1
     * @param rowBlock2
     * @param columnBlock2
     */
    public void didClearTwoBlocksUnsuccessful(int rowBlock1, int columnBlock1, int rowBlock2, int columnBlock2);

    /**
     * 重置界面。用于游戏启动时对界面的初始化以及用户重新开始游戏的操作。
     */
    public void reset();



    // TODO 用户点击的事件  是由view自动更新还是先由view交给controller，再由controller调用view更新？
    // TODO 进度条的时间由谁来维护？

}