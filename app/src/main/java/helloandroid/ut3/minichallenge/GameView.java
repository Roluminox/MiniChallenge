package helloandroid.ut3.minichallenge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private int screenWidth;
    private int screenHeight;
    private int circleRadius = 30; // Modifier le rayon du cercle si nécessaire
    private int circleCenterX;
    private int circleCenterY;
    private int speedX = 5; // Vitesse de déplacement horizontale
    private int speedY = 5; // Vitesse de déplacement verticale
    private List<Stickman> stickmanList;

    private boolean isDark = false; // False si light on

    public GameView(Context context) {
        super(context);
        thread = new GameThread(context, getHolder(), this);
        setFocusable(true);
        getHolder().addCallback(this);

        stickmanList = new ArrayList<>();
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

            // Zone de destruction
            Paint paintDestruction = new Paint();
            paintDestruction.setColor(Color.YELLOW);
            canvas.drawCircle(getRootView().getWidth() / 2, getRootView().getHeight() / 2, 350, paintDestruction);

            // Zone à protéger
            Paint paintZone = new Paint();
            paintZone.setColor(Color.GREEN);
            paintZone.setStyle(Paint.Style.STROKE); // Style du contour du cercle
            paintZone.setStrokeWidth(5); // Épaisseur
            canvas.drawCircle(getRootView().getWidth() / 2, getRootView().getHeight() / 2, 100, paintZone);

            // Boule qui bouge
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, paint);

            paintStickman(canvas);
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

    public void paintStickman(Canvas canvas) {
        Random random = new Random();
        // Générer un nombre aléatoire entre 0 et 3
        int randomCoin = random.nextInt(4);

        Stickman newStickman = null;
        switch(randomCoin) {
            // Haut
            case 0:
                int randomTop = random.nextInt(screenWidth);
                newStickman = new Stickman(randomTop, 0);
                break;
            // Droite
            case 1:
                int randomRight = random.nextInt(screenHeight);
                newStickman = new Stickman(screenWidth-50, randomRight);
                break;
            // Gauche
            case 2:
                int randomLeft = random.nextInt(screenHeight);
                newStickman = new Stickman(0, randomLeft);
                break;
            // Bas
            case 3:
                int randomBottom = random.nextInt(screenWidth);
                newStickman = new Stickman(randomBottom, screenHeight-50);
        }

        stickmanList.add(newStickman);

        for(Stickman stickman : stickmanList) {
            canvas.drawRect(stickman.getStickman(), stickman.getPaint());
        }
    }
}
