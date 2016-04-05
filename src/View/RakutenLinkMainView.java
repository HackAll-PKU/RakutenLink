package View;

import Controller.RakutenLinkBlockDataSource;
import Controller.RakutenLinkMainController;
import Controller.RakutenLinkViewDelegate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ChenLetian on 4/1/16.
 * RakutenLink的主View类
 */

public class RakutenLinkMainView extends AbstractViewPanel implements ViewUpdatable{
    // 主Panel
    private JPanel rakutenLinkMainView;
    private JFrame rakutenLinkMainFrame;
    private RakutenLinkBlockDataSource dataSource;
    private RakutenLinkViewDelegate delegate;
    private RakutenLinkBlock[][] buttons;

    public RakutenLinkMainView(RakutenLinkBlockDataSource dataSource, RakutenLinkViewDelegate delegate) {
        this.dataSource = dataSource;
        this.delegate = delegate;
    }

    /**
     * 初始化主界面
     */
    public void initializeRakutenLinkMainView(){
        rakutenLinkMainView = new JPanel();

        rakutenLinkMainView.setBorder(new EmptyBorder(10, 20, 10, 20));
        rakutenLinkMainView.setLayout(new BorderLayout(0, 0));

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(10,20));
        rakutenLinkMainView.add(gridPanel, BorderLayout.CENTER);

        buttons = new RakutenLinkBlock[dataSource.rowNumber()][dataSource.columnNumber()];
        for( int i=0; i<dataSource.columnNumber() * dataSource.rowNumber(); i++) {
            int row = i / dataSource.columnNumber();
            int column = i % dataSource.columnNumber();
            buttons[row][column] = new RakutenLinkBlock(row, column, String.valueOf(dataSource.typeForBlockAtRowAndColumn(row, column)));
            buttons[row][column].addActionListener(e -> {
                RakutenLinkBlock source = (RakutenLinkBlock) e.getSource();
                delegate.DidClickBlockAtRowAndColumn(source.row, source.column);
            });
        }
        for (RakutenLinkBlock[] buttonRow : buttons)
            for (RakutenLinkBlock button: buttonRow)
                gridPanel.add(button);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));
        rakutenLinkMainView.add(buttonPanel,BorderLayout.SOUTH);

        JButton buttonNew = new JButton("New Game");
        JButton buttonShuffle = new JButton("重排");
        buttonPanel.add(buttonNew);
        buttonPanel.add(buttonShuffle);
        buttonNew.addActionListener(e -> {
            delegate.restartGame();
        });
        buttonShuffle.addActionListener(e -> {
            delegate.reset();
        });

        JPanel timelinePanel= new JPanel();
        timelinePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel timeline=new JLabel();
        timeline.setSize(100, 100);
        timeline.setText("Time Remain: 60s");
        timeline.setBackground(Color.black);
        timelinePanel.add(timeline);
        rakutenLinkMainView.add(timelinePanel,BorderLayout.NORTH);

        rakutenLinkMainFrame = new JFrame("乐天连连看");
        rakutenLinkMainFrame.setContentPane(this.rakutenLinkMainView);
        rakutenLinkMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rakutenLinkMainFrame.setPreferredSize(new Dimension(800, 600));
        rakutenLinkMainFrame.pack();
        rakutenLinkMainFrame.setVisible(true);
    }

    @Override
    public void makeBlockSelected(int rowBlock, int columnBlock) {

    }

    @Override
    public void makeBlockUnselected(int rowBlock, int columnBlock) {
        // 通过让panel获得焦点的方式让已经被选择的块失去焦点
        this.rakutenLinkMainView.requestFocus();
    }

    @Override
    public void didClearTwoBlocksSuccessful(int rowBlock1, int columnBlock1, int rowBlock2, int columnBlock2) {
        // 现在还没有加入消去的动画和连线
        buttons[rowBlock1][columnBlock1].setVisible(false);
        buttons[rowBlock2][columnBlock2].setVisible(false);
    }

    @Override
    public void didClearTwoBlocksUnsuccessful(int rowBlock1, int columnBlock1, int rowBlock2, int columnBlock2) {
        this.rakutenLinkMainView.requestFocus();
    }

    @Override
    public void noValidFutureActions() {
        // 将会提示用户不可以进行更多操作，并且强制重置游戏。
        // @author: archimekai, Zac Chan
        JOptionPane.showMessageDialog(this.rakutenLinkMainFrame, "There are no more blocks that you can link.","Info",
                JOptionPane.INFORMATION_MESSAGE);

        delegate.reset();
    }

    @Override
    public void noTimeRemaining() {
        // 通过弹出对话框的方式提醒用户时间到了
        JOptionPane.showMessageDialog(this.rakutenLinkMainFrame,"Time is up! Game will restart.","Info",JOptionPane.INFORMATION_MESSAGE);
        delegate.reset();
    }

    @Override
    public void didSuccess() {
        // status: 函数完成 by Archimekai
        Object[] options = {"Exit game","Play again"};
        int chosen = JOptionPane.showOptionDialog(this.rakutenLinkMainFrame,"You win! ","Info",JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
        switch (chosen){
            case 0:
                delegate.shutDown();
                break;
            case 1:
                delegate.reset();
                break;
            default:
                break;
        }

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
        for( int i=0; i<dataSource.columnNumber() * dataSource.rowNumber(); i++) {
            int row = i / dataSource.columnNumber();
            int column = i % dataSource.columnNumber();
            buttons[row][column].setText(String.valueOf(dataSource.typeForBlockAtRowAndColumn(row, column)));
        }
    }
}
