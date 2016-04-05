package Controller;

import Model.RakutenLinkModel;
import View.RakutenLinkMainView;

import java.beans.PropertyChangeEvent;

/**
 * Created by ChenLetian on 4/1/16.
 * RakutenLink的主Controller类
 */
public class RakutenLinkMainController extends AbstractController implements RakutenLinkViewDelegate, RakutenLinkBlockDataSource {

    private RakutenLinkMainView mainView;
    private RakutenLinkModel mainModel;

    final int blockTypes = 20;
    final int rowNumber = 10;
    final int columnNumber = 20;

    public RakutenLinkMainController() {
        mainModel = new RakutenLinkModel(rowNumber, columnNumber);
        mainModel.reset(blockTypes);
        addModel(mainModel);
        mainView = new RakutenLinkMainView(this, this);
        mainView.initializeRakutenLinkMainView();
        addView(mainView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        // TODO: to determine what kind of Event has been triggered and then call different method of View

    }

    public void shutDown(){
        // 以下为样例代码,仅仅体现了退出的逻辑 by Archimekai
        System.exit(0);
    }

    @Override
    public void reset(){
        mainModel.shuffle();
        mainView.reset();
    }

    @Override
    public void restartGame() {
        mainModel.reset(blockTypes);
        mainView.reset();
    }

    @Override
    public void DidClickBlockAtRowAndColumn(int row, int column) {
        System.out.printf("row=%d, column=%d has been selected\n", row, column);
        // TODO: let model know
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
