package helloandroid.ut3.minichallenge;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Stickman {

    private float x;
    private float y;
    private float deplacementX;
    private float deplacementY;

    private RectF stickman;
    private Paint paint;
    private int color;
    private boolean isDestructible;
    private int protectedZoneRadius;

    public Stickman(int x, int y, int centerX, int centerY) {
        initStickman(screenWidth, screenHeight);
        this.x = x;
        this.y = y;
        this.deplacementY = (Math.max(centerY, Math.abs(y)) - Math.min(centerY, Math.abs(y))) / 100f;
        this.deplacementX = (Math.max(centerX, Math.abs(x)) - Math.min(centerX, Math.abs(x))) / 100f;
        this.color = Color.RED;
        this.isDestructible = false;
        this.paint = new Paint();
        this.paint.setColor(Color.RED);
    }

    public void update(int centerX, int centerY, int centerWidth, int centerHeigth) {
        x = x-centerX < 0 ? x+deplacementX : x-deplacementX;
        y = y-centerY < 0 ? y+deplacementY : y-deplacementY;

        // Vérifiez si le nouveau Stickman est dans la zone de destruction
        if (isInDestructionZone(this, centerWidth, centerHeigth)) {
            setDestructible();
        }

        // Vérifier si le Stickman est dans la zone à protéger
        if (isInProtectedZone(centerX, centerY, protectedZoneRadius)){
            // Envoyer sur la page de fin de jeu
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

    public float getX() {
        return this.x;
    }

    public float getY() {
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
