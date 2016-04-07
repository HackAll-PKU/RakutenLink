package Model;

import Controller.RakutenLinkMainController;

/**
 * Created by ChenLetian on 4/7/16.
 * RakutenLink控制时间的类
 */
public class RakutenLinkTimer extends AbstractModel {

    private int gameTime; // in second
    private Thread timerThread;
    private Boolean alive;

    public RakutenLinkTimer(int gameTime) {
        super();
        this.gameTime = gameTime;
        alive = true;
    }

    public void start() {
        timerThread = new Thread(() -> {
            double progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(gameTime * 1000 / 100);
                } catch (InterruptedException ignore) {}
                progress += 1;
                setProgress(Math.min((int)progress, 100));
            }
            done();
        });
        timerThread.start();
    }

    private void setProgress(double progress) {
        if (alive) firePropertyChange(RakutenLinkMainController.GameTimeChange, null, progress);
    }

    protected void done() {
        if (alive) firePropertyChange(RakutenLinkMainController.GameTimesUp, null, null);
    }

    public void stop() {
        alive = false;
    }
}
