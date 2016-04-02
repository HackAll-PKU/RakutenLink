package View;

import Controller.RakutenLinkBlockDataSource;

import javax.swing.*;

/**
 * Created by ChenLetian on 4/1/16.
 * RakutenLink的主View类
 */

public class RakutenLinkMainView extends AbstractViewPanel implements ViewUpdatable{
    // 主Panel
    public JPanel RakutenLinkMainView;



    private RakutenLinkBlockDataSource dataSource;

    public RakutenLinkMainView(RakutenLinkBlockDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void makeBlockSelected(int rowBlock, int columnBlock) {

    }

    @Override
    public void makeBlockUnselected(int rowBlock, int columnBlock) {

    }

    @Override
    public void didClearTwoBlocksSuccessful(int rowBlock1, int columnBlock1, int rowBlock2, int columnBlock2) {

    }

    @Override
    public void didClearTwoBlocksUnsuccessful(int rowBlock1, int columnBlock1, int rowBlock2, int columnBlock2) {

    }

    @Override
    public void noValidFutureActions() {

    }

    @Override
    public void noTimeRemaining() {
        // 通过弹出对话框的方式提醒用户时间到了
        JOptionPane.showMessageDialog(this,"Time is up! Game will restart.","Info",JOptionPane.INFORMATION_MESSAGE);
        this.reload();
    }

    @Override
    public void didSuccess() {
        JOptionPane.showMessageDialog(this,"You win! Game will restart.","Info",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void reset() {
        reload();
    }

    @Override
    public void initialize() {
        reload();
    }


    /**
     * 从dataSource里面取得所有的label数据和状态并显示到view中。<br>
     * 如果需要让游戏初始化，则需要controller先调用model的reset接口，model中的数据初始化后，再调用view 的reset（）函数把重置好的数据加载进来。
     */
    private void reload(){

    }
}
