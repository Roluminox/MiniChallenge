package helloandroid.ut3.minichallenge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Stickman {

    private int x;
    private int y;
    private RectF stickman;
    private Paint paint;
    private int color;
    private boolean isDestructible;
    private int protectedZoneRadius;

    public Stickman(int screenWidth, int screenHeight) {
        initStickman(screenWidth, screenHeight);
        this.color = Color.RED;
        this.isDestructible = false;
        this.paint = new Paint();
        this.paint.setColor(Color.RED);
    }

    public void update(Context context, int centerX, int centerY, int centerWidth, int centerHeigth, int protectedZoneRadius, GameThread thread ) {
        x = x-centerX < 0 ? x+3 : x-3;
        y = y-centerY < 0 ? y+3 : y-3;

        // Vérifiez si le nouveau Stickman est dans la zone de destruction
        if (isInDestructionZone(this, centerWidth, centerHeigth)) {
            setDestructible();
        }

        // Vérifier si le Stickman est dans la zone à protéger
        if (isInProtectedZone(centerX, centerY, protectedZoneRadius)){
            // Envoyer sur la page de fin de jeu
            thread.setRunning(false);
            Intent intent = new Intent(context, EndActivity.class);
            context.startActivity(intent);
        }

        stickman = new RectF(x, y, x+50, y+50);
    }

    private void initStickman(int screenWidth, int screenHeight) {
        Random random = new Random();
        // Générer un nombre aléatoire entre 0 et 3
        int randomCoin = random.nextInt(4);

        switch(randomCoin) {
            // Haut
            case 0:
                this.x = random.nextInt(screenWidth);
                this.y = 0;
                break;
            // Droite
            case 1:
                this.x = screenWidth-50;
                this.y = random.nextInt(screenHeight);
                break;
            // Gauche
            case 2:
                this.x = 0;
                this.y = random.nextInt(screenHeight);
                break;
            // Bas
            case 3:
                this.x = random.nextInt(screenWidth);
                this.y = screenHeight-50;
        }
        stickman = new RectF(this.x, this.y, this.x+50, this.y+50);
    }

    public RectF getStickman() {
        return this.stickman;
    }

    public Paint getPaint() {
        return this.paint;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }


    public boolean isDestructible() {
        return this.isDestructible;
    }

    public void setDestructible() {
        this.isDestructible = true;
    }

    // Méthode pour vérifier si un Stickman est dans la zone de destruction
    private boolean isInDestructionZone(Stickman stickman, int centerWidth, int centerHeigth) {
        return Math.sqrt(Math.pow(stickman.getX() - centerWidth, 2) + Math.pow(stickman.getY() - centerHeigth, 2)) <= 400;
    }

    // Méthode pour vérifier si un Stickman est dans la zone protégée
    private boolean isInProtectedZone(int centerX, int centerY, int protectedZoneRadius) {
        double distanceToCenter = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        return distanceToCenter <= protectedZoneRadius;
    }
}
