package helloandroid.ut3.minichallenge;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import helloandroid.ut3.minichallenge.capteurs.SensorListenerCallback;
import helloandroid.ut3.minichallenge.capteurs.SensorManagerClass;

public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;

    private GameView gameView;

    private Canvas canvas;

    private boolean running;

    public GameThread(Context context, SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public GameThread() {

    }

    @Override public void run() {
        while (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {}
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

}
