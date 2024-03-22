package helloandroid.ut3.minichallenge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import helloandroid.ut3.minichallenge.capteurs.SensorListenerCallback;
import helloandroid.ut3.minichallenge.capteurs.SensorManagerClass;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorListenerCallback {
    private GameThread thread;
    private int screenWidth;
    private int screenHeight;
    private int circleRadius = 50; // Modifier le rayon du cercle si nécessaire
    private int circleCenterX;
    private int circleCenterY;
    private int speedX = 5; // Vitesse de déplacement horizontale
    private int speedY = 5; // Vitesse de déplacement verticale
    private int movementX = 0;
    private int movementY = 0;

    public GameView(Context context) {
        super(context);
        thread = new GameThread(context, getHolder(), this);
        setFocusable(true);
        getHolder().addCallback(this);
        SensorManagerClass sensorManager = new SensorManagerClass(context, this);
        sensorManager.registerListener();
    }

    public void update() {
        // Mettre à jour les coordonnées du cercle pour le déplacer
        if(
                movementX > 0 && (circleCenterX + circleRadius) + movementX <= screenWidth ||
                movementX < 0 && (circleCenterX - circleRadius) + movementX >= 0
        ){
            circleCenterX += movementX;
        }

        if(
                movementY > 0 && (circleCenterY + circleRadius) + movementY <= screenHeight ||
                movementY < 0 && (circleCenterY - circleRadius) + movementY >= 0
        ) {
            circleCenterY += movementY;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, paint);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
        // Initialiser les coordonnées du centre du cercle au centre de l'écran
        circleCenterX = screenWidth / 2;
        circleCenterY = screenHeight / 2;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void onLuxValueChange(float luxValue) {
        //Do nothing
    }

    @Override
    public void onAccValueChange(double[] accValue) {
        this.movementX = (int) accValue[1];
        this.movementY = (int) accValue[0];
    }
}
