package View;

import Controller.RakutenLinkBlockDataSource;
import Controller.RakutenLinkMainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

/**
 * Created by ChenLetian on 4/1/16.
 * RakutenLink的主View类
 */

public class RakutenLinkMainView extends AbstractViewPanel implements ViewUpdatable{
    // 主Panel
    public JPanel RakutenLinkMainView;
    private RakutenLinkBlockDataSource dataSource;
    private RakutenLinkMainController controller;

    public RakutenLinkMainView(RakutenLinkBlockDataSource dataSource, RakutenLinkMainController controller) {
        this.dataSource = dataSource;
        this.controller = controller;
        initializeRakutenLinkMainView(); // 初始化主界面

    }

    /**
     * 初始化主界面
     *
     * @author 刘证源
     */
    private void initializeRakutenLinkMainView(){
        RakutenLinkMainView.setBorder(new EmptyBorder(10, 20, 10, 20));
        RakutenLinkMainView.setLayout(new BorderLayout(0, 0));

        JPanel gridPanel = new JPanel();  //centerµÄ¿éÃæ°å
        gridPanel.setLayout(new GridLayout(10,20));
        RakutenLinkMainView.add(gridPanel, BorderLayout.CENTER);

        JButton[] buttons=new JButton[200];  //Á¬Á¬¿´µÄ¿é
        for( int i=0; i<buttons.length; i++)
            buttons[i]=new JButton(""+(i+1));
        for(int i=0;i<buttons.length;i++)
            gridPanel.add(buttons[i]);

        JPanel buttonPanel = new JPanel();  //µ×²¿µÄ°´Å¥Ãæ°å
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));
        RakutenLinkMainView.add(buttonPanel,BorderLayout.SOUTH);

        JButton buttonNew = new JButton("New Game");  //ÐÂÓÎÏ·°´Å¥
        JButton buttonRestart = new JButton("Restart Game");  //ÖØÐÂ¿ªÊ¼ÓÎÏ·°´Å¥
        //JButton buttonPause = new JButton("Pause");  //ÔÝÍ£°´Å¥
        buttonPanel.add(buttonNew);
        buttonPanel.add(buttonRestart);
        //buttonPanel.add(buttonPause);

        JPanel timelinePanel= new JPanel();  //¶¥²¿µÄÊ±¼äÖáÃæ°å
        timelinePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel timeline=new JLabel();
        timeline.setSize(100, 100);
        timeline.setText("Time Remain: 60s");
        timeline.setBackground(Color.black);
        timelinePanel.add(timeline);
        RakutenLinkMainView.add(timelinePanel,BorderLayout.NORTH);
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
        // 将会提示用户不可以进行更多操作，并且强制重置游戏。
        // @author: archimekai
        JOptionPane.showMessageDialog(this, "There are no more blocks that you can link.","Info",
                JOptionPane.INFORMATION_MESSAGE);

        controller.reset();
    }

    @Override
    public void noTimeRemaining() {
        // 通过弹出对话框的方式提醒用户时间到了
        JOptionPane.showMessageDialog(this,"Time is up! Game will restart.","Info",JOptionPane.INFORMATION_MESSAGE);
        controller.reset();
    }

    @Override
    public void didSuccess() {
        // status: 函数完成 by Archimekai
        Object[] options = {"Exit game","Play again"};
        int chosen = JOptionPane.showOptionDialog(this,"You win! ","Info",JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
        switch (chosen){
            case 0:
                controller.shutDown();
                break;
            case 1:
                controller.reset();
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

    }
}
