package helloandroid.ut3.minichallenge;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Stickman {

    private float x;
    private float y;
    private float deplacementX;
    private float deplacementY;

    private RectF stickman;
    private Paint paint;
    private int color;
    private boolean isDestructible;

    public Stickman(int x, int y, int centerX, int centerY) {
        this.x = x;
        this.y = y;
        this.deplacementY = (Math.max(centerY, Math.abs(y)) - Math.min(centerY, Math.abs(y))) / 100f;
        this.deplacementX = (Math.max(centerX, Math.abs(x)) - Math.min(centerX, Math.abs(x))) / 100f;
        this.color = Color.RED;
        this.isDestructible = false;
        this.stickman = new RectF(this.x, this.y, this.x+50, this.y+50);
        this.paint = new Paint();
        this.paint.setColor(Color.RED);
    }

    public void update(int centerX, int centerY, int centerWidth, int centerHeigth) {
        x = x-centerX < 0 ? x+deplacementX : x-deplacementX;
        y = y-centerY < 0 ? y+deplacementY : y-deplacementY;

        // Vérifiez si le nouveau Stickman est dans la zone de destruction
        if (isInDestructionZone(this, centerWidth, centerHeigth)) {
            this.setPaintColor(Color.GREEN);
            setDestructible();
        }

        stickman = new RectF(x, y, x+50, y+50);
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

    public void setPaintColor(int color) {
        this.color = color;
        this.paint.setColor(color); // Mettre à jour la couleur du pinceau
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
}
