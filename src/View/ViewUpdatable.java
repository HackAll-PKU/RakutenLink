package View;

/**
 * Created by archimekai on 16/4/1.
 * 此接口说明了更新界面时提供的函数
 * 这些函数应该由controller调用
 */
public interface ViewUpdatable {

    /**
     * 使特定方块处于选中状态，需要知道选中方块的横纵坐标。
     *
     * @param rowBlock 选中方块的横坐标
     * @param columnBlock 选中方块的纵坐标
     */
    public void makeBlockSelected(int rowBlock, int columnBlock);

    /**
     * 使特定方块从被选中状态转变为未被选中状态，需要知道被操作方块的横纵坐标
     *
     * @param rowBlock
     * @param columnBlock
     */
    public void makeBlockUnselected(int rowBlock, int columnBlock);


    /**
     * 当两个方块被成功消去时调用,需要被消去的两个方块的横纵坐标,用于绘制消去成功时的有关动画和声音, 并且会将有关的方块设置为不可见
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
     * 通知view游戏进入了死局。
     */
    public void noValidFutureActions();

    /**
     * 通知view游戏剩余时间已耗尽。
     */
    public void noTimeRemaining();

    /**
     * 成功完成游戏。
     */
    public void didSuccess();

    /**
     * 重置界面。用于游戏启动时对界面的初始化以及用户重新开始游戏的操作。
     */
    public void reset();

    /**
     * 初始化界面。
     */
    public void initialize();


    // TODO 进度条的时间由谁来维护？

}