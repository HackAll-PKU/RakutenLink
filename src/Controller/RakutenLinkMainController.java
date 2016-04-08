package Controller;

import Model.RakutenLinkModel;
import Model.RakutenLinkTimer;
import View.RakutenLinkMainView;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.util.Objects;

/**
 * Created by ChenLetian on 4/1/16.
 * RakutenLink的主Controller类
 */
public class RakutenLinkMainController extends AbstractController implements RakutenLinkViewDelegate, RakutenLinkBlockDataSource {

    private RakutenLinkMainView mainView;
    private RakutenLinkModel mainModel;
    private RakutenLinkTimer timer;

    public static final String GameHasWon = "GameWin";
    public static final String GameHasNoBlocksToClear = "GameHasNoBlocksToClear";
    public static final String GameTimesUp = "GameTimesUp";
    public static final String GameTimeChange = "GameTimeChange";

    //*
    final int blockTypes = 20;
    final int rowNumber = 10;
    final int columnNumber = 20;
    final int gameTime = 60;
    final int clearSuccessAddTime = 5;
    final int clearUnSuccessMinusTime = 5;
    /*/
    final int blockTypes = 3;
    final int rowNumber = 5;
    final int columnNumber = 6;
    final int gameTime = 30;
    //*/

    public int shuffleCount;
    public int hintCount;

    public RakutenLinkMainController() {
        mainModel = new RakutenLinkModel(rowNumber, columnNumber);
        mainModel.reset(blockTypes);
        addModel(mainModel);
        mainView = new RakutenLinkMainView(this, this);
        mainView.initializeRakutenLinkMainView(rowNumber, columnNumber);
        addView(mainView);
        mainView.askForReady();
        resetSelectStatus();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        if (Objects.equals(evt.getPropertyName(), GameHasWon)) {
            timer.stop();
            mainView.didSuccess();
        }
        else
        if (Objects.equals(evt.getPropertyName(), GameHasNoBlocksToClear)) {
            mainView.reload();
        }
        else
        if (Objects.equals(evt.getPropertyName(), GameTimesUp)) {
            mainView.noTimeRemaining();
        }
        else
        if (Objects.equals(evt.getPropertyName(), GameTimeChange)) {
            double remainingTime = (double)evt.getNewValue();
            SwingUtilities.invokeLater(() -> mainView.updateTime(remainingTime / gameTime, remainingTime));
        }
    }

    @Override
    public void startGame() {
        timer = new RakutenLinkTimer(gameTime);
        timer.addPropertyChangeListener(this);
        timer.start();
        shuffleCount=3;
        hintCount=3;
    }

    public void shutDown(){
        // 以下为样例代码,仅仅体现了退出的逻辑 by Archimekai
        System.exit(0);
    }

    @Override
    public void shuffle(){
        mainModel.shuffle();
        mainView.reload();
        resetSelectStatus();
    }

    @Override
    public int[][] requestHint() {
        if (hintCount > 0) {
            mainView.updateCheatStatus(1,--hintCount);
            return mainModel.getHint();
        }
        else return new int[][]{};
    }

    @Override
    public void requestShuffle() {
        if (shuffleCount > 0){
            shuffle();
            mainView.updateCheatStatus(0,--shuffleCount);
        }
    }

    @Override
    public void restartGame() {
        timer.stop();
        mainModel.reset(blockTypes);
        mainView.reload();
        resetSelectStatus();
        mainView.updateCheatStatus(0, shuffleCount = 3);
        mainView.updateCheatStatus(1, hintCount = 3);
        mainView.askForReady();
    }

    private int hasSelectedCount;
    private int hasSelectedRow;
    private int hasSelectedColumn;

    private void resetSelectStatus() {
        hasSelectedCount = 0;
        hasSelectedRow = -1;
        hasSelectedColumn = -1;
    }

    @Override
    public void DidSelectionCanceled() {
        this.resetSelectStatus();
    }

    @Override
    public void DidClickBlockAtRowAndColumn(int row, int column) {
        if (row == hasSelectedRow && column == hasSelectedColumn) return;

        hasSelectedCount++;
        if (hasSelectedCount == 1) {
            hasSelectedRow = row;
            hasSelectedColumn = column;
        }
        else {
            int[][] linkNodes=mainModel.getLinkNodes(hasSelectedRow, hasSelectedColumn, row, column);
            if(linkNodes.length!=0){
            //if (mainModel.Removable(hasSelectedRow, hasSelectedColumn, row, column)) {
                mainModel.clearTwoBlocks(hasSelectedRow, hasSelectedColumn, row, column);
                timer.addTime(clearSuccessAddTime);
                if ((hasSelectedRow != -1)) {
                    mainView.didClearTwoBlocksSuccessful(hasSelectedRow, hasSelectedColumn, row, column);
                }
                //TODO:draw line
                resetSelectStatus();
            }
            else {
                mainView.didClearTwoBlocksUnsuccessful(hasSelectedRow, hasSelectedColumn, row, column);
                timer.minusTime(clearUnSuccessMinusTime);
                hasSelectedCount = 1;
                hasSelectedRow = row;
                hasSelectedColumn = column;
            }
        }
    }

    @Override
    public int typeForBlockAtRowAndColumn(int row, int column) {
        return mainModel.getTypeOfRowAndColumn(row, column);
    }

    @Override
    public int rowNumber() {
        return mainModel.getRowNumber();
    }

    @Override
    public int columnNumber() {
        return mainModel.getColumnNumber();
    }
}
