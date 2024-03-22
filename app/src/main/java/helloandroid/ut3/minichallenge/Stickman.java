package helloandroid.ut3.minichallenge;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Stickman {

    private int x;
    private int y;
    private RectF stickman;
    private Paint paint;
    private int color;

    public Stickman(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.RED;
        this.stickman = new RectF(this.x, this.y, this.x+50, this.y+50);
        this.paint = new Paint();
        this.paint.setColor(Color.RED);
    }

    public void update(int centerX, int centerY, int centerWidth, int centerHeigth) {
        x = x-centerX < 0 ? x+1 : x-1;
        y = y-centerY < 0 ? y+1 : y-1;

        // Vérifiez si le nouveau Stickman est dans la zone de destruction
        if (isInDestructionZone(this, centerWidth, centerHeigth)) {
            this.setPaintColor(Color.GREEN);
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

    public void setPaintColor(int color) {
        this.color = color;
        this.paint.setColor(color); // Mettre à jour la couleur du pinceau
    }

    // Méthode pour vérifier si un Stickman est dans la zone de destruction
    private boolean isInDestructionZone(Stickman stickman, int centerWidth, int centerHeigth) {
        return Math.sqrt(Math.pow(stickman.getX() - centerWidth, 2) + Math.pow(stickman.getY() - centerHeigth, 2)) <= 400;
    }
}
