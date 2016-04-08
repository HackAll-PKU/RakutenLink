package View;

import Controller.RakutenLinkBlockDataSource;
import Controller.RakutenLinkViewDelegate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JProgressBar progressBar;
    private JLabel timeLine;
    private JLabel shuffleCountLabel;
    private JLabel hintCountLabel;
    private JButton buttonShuffle;
    private JButton buttonHint;

    public RakutenLinkMainView(RakutenLinkBlockDataSource dataSource, RakutenLinkViewDelegate delegate) {
        this.dataSource = dataSource;
        this.delegate = delegate;
    }

    /**
     * 初始化主界面
     */
    public void initializeRakutenLinkMainView(int rowNumber, int columnNumber){
        // main Panel
        rakutenLinkMainView = new JPanel();
        rakutenLinkMainView.setBorder(new EmptyBorder(0, 0, 0, 0));
        rakutenLinkMainView.setLayout(new BorderLayout(0, 0));

        // center Panel
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rowNumber, columnNumber));
        rakutenLinkMainView.add(gridPanel, BorderLayout.CENTER);

        buttons = new RakutenLinkBlock[dataSource.rowNumber()][dataSource.columnNumber()];
        for( int i=0; i<dataSource.columnNumber() * dataSource.rowNumber(); i++) {
            int row = i / dataSource.columnNumber();
            int column = i % dataSource.columnNumber();
            buttons[row][column] = new RakutenLinkBlock(row, column);
            buttons[row][column].setVisible(dataSource.typeForBlockAtRowAndColumn(row, column) != -1);
            buttons[row][column].addActionListener(e -> {
                RakutenLinkBlock source = (RakutenLinkBlock) e.getSource();
                delegate.DidClickBlockAtRowAndColumn(source.row, source.column);
            });
        }
        for (RakutenLinkBlock[] buttonRow : buttons)
            for (RakutenLinkBlock button: buttonRow)
                gridPanel.add(button);

        // south panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));
        rakutenLinkMainView.add(buttonPanel,BorderLayout.SOUTH);

        JButton buttonNew = new JButton("New Game");
        buttonShuffle = new JButton("Shuffle");
        buttonHint = new JButton("Hint");
        buttonPanel.add(buttonNew);
        buttonPanel.add(buttonShuffle);
        buttonPanel.add(buttonHint);
        buttonNew.addActionListener(e -> {
            delegate.restartGame();
            this.rakutenLinkMainView.requestFocus();
        });
        buttonShuffle.addActionListener(e -> {
            delegate.requestShuffle();
            this.rakutenLinkMainView.requestFocus();
        });
        buttonHint.addActionListener(e -> {
            int[][] hint = delegate.requestHint();
            SwingUtilities.invokeLater(() -> {
                for (int[] coor : hint) {
                    JButton btn = buttons[coor[0]][coor[1]];
                    Graphics g = btn.getGraphics();
                    g.setColor(new Color(129, 216, 207, 128));
                    g.fillRect(0, 0, btn.getWidth(), btn.getHeight());
                }
            });
            this.rakutenLinkMainView.requestFocus();
        });
        rakutenLinkMainView.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ((JPanel)e.getSource()).requestFocus();
                delegate.DidSelectionCanceled();
            }
        });

        // north panel
        JPanel timelinePanel= new JPanel();
        timelinePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        progressBar = new JProgressBar(0, 100);
        timelinePanel.add(progressBar);
        progressBar.setValue(0);
        timeLine=new JLabel();
        timeLine.setSize(100, 100);
        timeLine.setText("Time Remain: 60.0s");
        timeLine.setBackground(Color.black);
        shuffleCountLabel=new JLabel();
        shuffleCountLabel.setSize(100, 100);
        shuffleCountLabel.setText("Shuffle: 3");
        shuffleCountLabel.setBackground(Color.black);
        hintCountLabel=new JLabel();
        hintCountLabel.setSize(100, 100);
        hintCountLabel.setText("Hint: 3");
        hintCountLabel.setBackground(Color.black);
        timelinePanel.add(timeLine);
        timelinePanel.add(shuffleCountLabel);
        timelinePanel.add(hintCountLabel);
        rakutenLinkMainView.add(timelinePanel,BorderLayout.NORTH);

        rakutenLinkMainFrame = new JFrame("乐天连连看");
        rakutenLinkMainFrame.setContentPane(this.rakutenLinkMainView);
        rakutenLinkMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rakutenLinkMainFrame.setPreferredSize(new Dimension(800, 600));
        rakutenLinkMainFrame.setResizable(false);
        rakutenLinkMainFrame.pack();
        rakutenLinkMainFrame.setVisible(true);
        this.rakutenLinkMainView.requestFocus();

        reload();
    }

    @Override
    public void didClearTwoBlocksSuccessful(int rowBlock1, int columnBlock1, int rowBlock2, int columnBlock2) {
        // 现在还没有加入消去的动画和连线
        reloadAtRowAndColumn(rowBlock1, columnBlock1);
        reloadAtRowAndColumn(rowBlock2, columnBlock2);
        this.rakutenLinkMainView.requestFocus();
    }

    @Override
    public void didClearTwoBlocksUnsuccessful(int rowBlock1, int columnBlock1, int rowBlock2, int columnBlock2) {
        buttons[rowBlock2][columnBlock2].requestFocus();
    }

    @Override
    public void noTimeRemaining() {
        // 通过弹出对话框的方式提醒用户时间到了
        JOptionPane.showMessageDialog(this.rakutenLinkMainFrame,"Time is up! Game will restart.","Info",JOptionPane.INFORMATION_MESSAGE);
        delegate.restartGame();
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
                delegate.restartGame();
                break;
            default:
                break;
        }
    }

    @Override
    public void reload() {
        for( int i=0; i<dataSource.columnNumber() * dataSource.rowNumber(); i++) {
            int row = i / dataSource.columnNumber();
            int column = i % dataSource.columnNumber();
            buttons[row][column].setIcon(getIconAtRowAndColumn(row, column));
            buttons[row][column].setVisible(dataSource.typeForBlockAtRowAndColumn(row, column)!=-1);
        }
    }

    @Override
    public void updateTime(double progress, double remainingTime) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue((int)(progress * 100));
            timeLine.setText("Time Remain: " + String.format("%04.1f", remainingTime) + "s");
        });
    }

    @Override
    public void updateCheatStatus(int type, int value) {
        //shuffle
        if(type==0){
            SwingUtilities.invokeLater(() -> {
                shuffleCountLabel.setText("Shuffle: " + String.valueOf(value));
                buttonShuffle.setEnabled(value > 0);
            });
        }
        //hint
        if(type==1){
            SwingUtilities.invokeLater(() -> {
                hintCountLabel.setText("Hint: " + String.valueOf(value));
                buttonHint.setEnabled(value > 0);
            });
        }
    }

    @Override
    public void askForReady() {
        while (JOptionPane.showConfirmDialog(this.rakutenLinkMainFrame, "Are you ready?", "Ready", JOptionPane.YES_NO_OPTION) == 1);
        delegate.startGame();
    }

    private void reloadAtRowAndColumn(int row, int column) {
        SwingUtilities.invokeLater(()->{
            buttons[row][column].setIcon(getIconAtRowAndColumn(row, column));
            buttons[row][column].setVisible(dataSource.typeForBlockAtRowAndColumn(row, column)!=-1);
        });
    }

    private ImageIcon getIconAtRowAndColumn(int row, int column) {
        if (dataSource.typeForBlockAtRowAndColumn(row, column) == -1) return null;
        else {
            ImageIcon icon = new ImageIcon("resource/" + String.valueOf(dataSource.typeForBlockAtRowAndColumn(row, column)) + ".png");
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(buttons[row][column].getWidth(), buttons[row][column].getHeight(), Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
    }
}
