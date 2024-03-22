package helloandroid.ut3.minichallenge;

import android.content.Context;
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

    private RectF flashlight;
    private int sizeFlashlight = 100;
    private int startAngle = 0;
    private int sweepAngle = 30;

    private Paint paint;
    private int color;
    private boolean isDestructible;

    public Stickman(int screenWidth, int screenHeight) {
        initStickman(screenWidth, screenHeight);
        this.color = Color.RED;
        this.isDestructible = false;
        this.paint = new Paint();
        this.paint.setColor(Color.RED);
    }

    public void update(Context context, int centerX, int centerY, int centerWidth, int centerHeigth, boolean isDark) {
        int div = isDark ? 1 : 3;
        x = x-centerX < 0 ? x+(deplacementX/div) : x-(deplacementX/div);
        y = y-centerY < 0 ? y+(deplacementY/div) : y-(deplacementY/div);

        // Vérifiez si le nouveau Stickman est dans la zone de destruction
        if (isInDestructionZone(this, centerWidth, centerHeigth)) {
            setDestructible();
        }

        // Update de la lampe si dans le noir
        if (true) {
            flashlight = new RectF(x - sizeFlashlight, y - sizeFlashlight, x + sizeFlashlight + 50, y + sizeFlashlight + 50);
            // Calcul startAngle and sweepAngle
            double deltaX = centerWidth - x;
            double deltaY = centerHeigth - y;
            double angleRadians = Math.atan2(deltaY, deltaX);
            double angleDegrees = Math.toDegrees(angleRadians);
            if (angleDegrees < 0) {
                angleDegrees += 360;
            }
            startAngle = (int)angleDegrees - 15;
        }

        stickman = new RectF(x, y, x+30, y+30);
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

        this.deplacementY = (Math.max(screenHeight/2, Math.abs(y)) - Math.min(screenHeight/2, Math.abs(y))) / 150f;
        this.deplacementX = (Math.max(screenWidth/2, Math.abs(x)) - Math.min(screenWidth/2, Math.abs(x))) / 150f;

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

    public RectF getFlashlight() {
        return flashlight;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public int getSweepAngle() {
        return sweepAngle;
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
    public boolean isInProtectedZone(int centerX, int centerY, int protectedZoneRadius) {
        double distanceToCenter = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        return distanceToCenter <= protectedZoneRadius;
    }
}
