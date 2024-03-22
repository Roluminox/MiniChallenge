package helloandroid.ut3.minichallenge;

import static helloandroid.ut3.minichallenge.utils.FormesUtils.stickmanTouchCircle;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stickman {

    private float x;
    private float y;
    private float deplacementX;
    private float deplacementY;
    private RectF flashlight;
    private int sizeFlashlight = 100;
    private int startAngle = 0;
    private int sweepAngle = 30;
    private boolean isDestructible;
    private Drawable character ;


    public Stickman(int screenWidth, int screenHeight, Drawable character) {
        initStickman(screenWidth, screenHeight);
        this.isDestructible = false;
        this.character = character;
    }

    public void update(int centerX, int centerY, int centerWidth, int centerHeigth, int graalradius, boolean isDark) {
        int div = isDark ? 3 : 1;
        x = x-centerX < 0 ? x+(deplacementX/div) : x-(deplacementX/div);
        y = y-centerY < 0 ? y+(deplacementY/div) : y-(deplacementY/div);

        // Vérifiez si le nouveau Stickman est dans la zone de destruction
        int distanceMin = Double.valueOf(graalradius*1.5).intValue();
        if (stickmanTouchCircle(this, centerWidth, centerHeigth, distanceMin)) {
            setDestructible();
        }

        // Update de la lampe si dans le noir
        if (isDark) {
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

    public Bitmap getCharacter() {
        return ((BitmapDrawable) character).getBitmap();
    }

    // Méthode pour vérifier si un Stickman est dans la zone protégée
    public boolean isInProtectedZone(int centerX, int centerY, int protectedZoneRadius) {
        double distanceToCenter = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        return distanceToCenter <= protectedZoneRadius;
    }
}
