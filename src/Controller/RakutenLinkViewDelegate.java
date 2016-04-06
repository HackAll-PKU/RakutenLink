package Controller;

/**
 * RakutenLinkView的代理类,为View提供各种代理事件
 * Created by ChenLetian on 4/4/16.
 */
public interface RakutenLinkViewDelegate {

    /**
     * 退出程序时调用。
     */
    void shutDown();

    /**
     * 重置整个游戏，需要先重置model，再重置view
     */
    void shuffle();

    /**
     * 重新开始游戏，需要先重置model，再重置view
     */
    void restartGame();

    /**
     * 某行某列的方块被点击了
     * @param row 行
     * @param column 列
     */
    void DidClickBlockAtRowAndColumn(int row, int column);

}
