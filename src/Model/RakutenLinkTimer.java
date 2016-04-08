package Model;

import Controller.RakutenLinkMainController;

/**
 * Created by ChenLetian on 4/7/16.
 * RakutenLink控制时间的类
 */
public class RakutenLinkTimer extends AbstractModel {

    private final int gameTime; // in second
    private Thread timerThread;
    private Boolean alive;
    private double remainingTime;

    public RakutenLinkTimer(int gameTime) {
        super();
        this.gameTime = gameTime;
        alive = true;
    }

    public void start() {
        timerThread = new Thread(() -> {
            remainingTime = gameTime;
            //Initialize progress property.
            setRemainingTime(remainingTime);
            while (remainingTime > 0) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignore) {}
                remainingTime -= 0.1;
                setRemainingTime(remainingTime);
            }
            done();
        });
        timerThread.start();
    }

    public void addTime(double time) {
        remainingTime += time;
        remainingTime = Math.min(remainingTime, gameTime);
    }

    public void minusTime(double time) {
        remainingTime -= time;
        remainingTime = Math.max(remainingTime, 0);
    }

    private void setRemainingTime(double remainingTime) {
        if (alive) firePropertyChange(RakutenLinkMainController.GameTimeChange, null, remainingTime);
    }

    protected void done() {
        if (alive) firePropertyChange(RakutenLinkMainController.GameTimesUp, null, null);
    }

    public void stop() {
        alive = false;
    }
}
