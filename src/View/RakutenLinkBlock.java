package View;

import javax.swing.*;
/**
 * Created by Archimekai on 4/1/2016.
 *
 * 连连看中每一个方块的类型。继承自JButton
 * 增加了row和column来记录该方块的位置，暂定以左上角的方块为坐标0，0, 以此为基准，向下为row增加的方向，向右为column增加的方向
 */
public class RakutenLinkBlock extends JButton {

    public final int row;
    public final int column;

    public RakutenLinkBlock(int row, int column, String label) {
        super(label);
        this.row = row;
        this.column = column;
    }

    public RakutenLinkBlock(Icon icon, int row, int column) {
        super(icon);
        this.row = row;
        this.column = column;
    }

    public RakutenLinkBlock(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
