package Controller;

/**
 * Created by ChenLetian on 4/2/16.
 * 消消乐的方块的数据源代理
 */
public interface RakutenLinkBlockDataSource {

    /**
     * 获取指定行列的方块的图片
     * @param row 行
     * @param column 列
     * @return 指定行列的图片,如果那个方块已经被消掉则为null
     */
    int typeForBlockAtRowAndColumn(int row, int column);

    /**
     * 获取棋盘有几行
     * @return 行数
     */
    int rowNumber();

    /**
     * 获取棋盘有几列
     * @return 列数
     */
    int columnNumber();

}
