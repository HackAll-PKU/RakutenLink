package Controller;

import View.RakutenLinkMainView;
import javax.swing.*;
import java.beans.PropertyChangeEvent;

/**
 * Created by ChenLetian on 4/1/16.
 * RakutenLink的主Controller类
 */
public class RakutenLinkMainController extends AbstractController {

    public RakutenLinkMainController() {
        addView(mainViewStatic);
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
        mainViewStatic = new RakutenLinkMainView();
        frame.setContentPane(mainViewStatic.RakutenLinkMainView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
