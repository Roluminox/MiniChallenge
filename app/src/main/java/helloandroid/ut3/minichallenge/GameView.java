package helloandroid.ut3.minichallenge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private int screenWidth;
    private int screenHeight;
    private int circleRadius = 30; // Modifier le rayon du cercle si nécessaire
    private int circleCenterX;
    private int circleCenterY;
    private int speedX = 5; // Vitesse de déplacement horizontale
    private int speedY = 5; // Vitesse de déplacement verticale

    private boolean isDark = false; // False si light on

    public GameView(Context context) {
        super(context);
        thread = new GameThread(context, getHolder(), this);
        setFocusable(true);
        getHolder().addCallback(this);
    }

    public void update() {
        // Mettre à jour les coordonnées du cercle pour le déplacer
        circleCenterX += speedX;
        circleCenterY += speedY;

        // Si le cercle atteint les bords de l'écran, inverser la direction
        if (circleCenterX + circleRadius >= screenWidth || circleCenterX - circleRadius <= 0) {
            speedX *= -1;
        }
        if (circleCenterY + circleRadius >= screenHeight || circleCenterY - circleRadius <= 0) {
            speedY *= -1;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            if (isDark)
                canvas.drawColor(Color.WHITE);
            else
                canvas.drawColor(Color.BLACK);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, paint);

            // Zone à protéger
            Paint paintZone = new Paint();
            paintZone.setColor(Color.GREEN);
            paintZone.setStyle(Paint.Style.STROKE); // Style du contour du cercle
            paintZone.setStrokeWidth(5); // Épaisseur
            canvas.drawCircle(getRootView().getWidth() / 2, getRootView().getHeight() / 2, 100, paintZone);
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

    public void setIsDark(boolean isDark) {
        this.isDark = isDark;
    }
}
