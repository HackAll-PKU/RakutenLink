package Controller;

import Model.AbstractModel;
import Model.RakutenLinkMainModel;
import View.AbstractViewPanel;
import View.RakutenLinkMainView;
import org.w3c.dom.views.AbstractView;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;

/**
 * Created by ChenLetian on 4/1/16.
 * RakutenLink的主Controller类
 */
public class RakutenLinkMainController extends AbstractController {

    public RakutenLinkMainController() {
        addView(mainViewStatic);
        addModel(new RakutenLinkMainModel());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        // TODO: to determine what kind of Event has been triggered and then call different method of View

    }

    // Below is the setup of the application
    private static JFrame frame;
    private static RakutenLinkMainView mainViewStatic;

    public static void main(String[] args) {
        frame = new JFrame("RakutenLinkMainView");
        mainViewStatic = new RakutenLinkMainView(,this);
        frame.setContentPane(mainViewStatic.RakutenLinkMainView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * 退出程序时调用。
     */
    public void shutDown(){
        // 以下为样例代码,仅仅体现了退出的逻辑 by Archimekai
        System.exit(0);
    }

    /**
     * 重置整个游戏，需要先重置model，再重置view
     */
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

            }
        }

    }

}
