package helloandroid.ut3.minichallenge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Stickman {

    private int x;
    private int y;
    private RectF stickman;
    private Paint paint;
    private int color;
    private boolean isDestructible;
    private int protectedZoneRadius;

    public Stickman(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.RED;
        this.isDestructible = false;
        this.stickman = new RectF(this.x, this.y, this.x+50, this.y+50);
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
