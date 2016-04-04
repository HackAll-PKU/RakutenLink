package Controller;

import Model.AbstractModel;
import Model.RakutenLinkMainModel;
import View.AbstractViewPanel;
import View.RakutenLinkMainView;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;

/**
 * Created by ChenLetian on 4/1/16.
 * RakutenLink的主Controller类
 */
public class RakutenLinkMainController extends AbstractController implements RakutenLinkViewDelegate, RakutenLinkBlockDataSource {

    private RakutenLinkMainView mainView;

    public RakutenLinkMainController() {
        mainView = new RakutenLinkMainView(this, this);
        mainView.initializeRakutenLinkMainView();
        addView(mainView);
        addModel(new RakutenLinkMainModel());
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

    public void reset(){
        // 以下为样例代码,仅仅体现了重置的逻辑 by Archimekai
        for (AbstractModel model: registeredModels
             ) {
            try{
                // 使用反射获取model中的reset方法
                Method method = model.getClass().getMethod("reset");
                method.invoke(model);
            }catch (Exception ex){
                // 什么也不做
            }
        }
        for (AbstractViewPanel viewPanel: registeredViews){
            try {
                Method method = viewPanel.getClass().getMethod("reset");
                method.invoke(viewPanel);
            } catch (Exception ex){
                // 什么也不做
            }
        }

    }

    @Override
    public void DidClickBlockAtRowAndColumn(int row, int column) {
        // TODO: let model know
    }

    @Override
    public ImageIcon typeForBlockAtRowAndColumn(int row, int column) {
        return null;
    }

    @Override
    public int rowNumber() {
        return 10;
    }

    @Override
    public int columnNumber() {
        return 20;
    }
}
