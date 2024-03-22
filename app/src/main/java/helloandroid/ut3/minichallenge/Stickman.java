package helloandroid.ut3.minichallenge;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Stickman {

    private int x;
    private int y;
    private RectF stickman;
    private Paint color;

    public Stickman(int x, int y) {
        stickman = new RectF(x, y, x+50, y+50);
        color = new Paint();
        color.setColor(Color.RED);
    }

    public void update() {

    }

    public RectF getStickman() {
        return this.stickman;
    }

    public Paint getPaint() {
        return this.color;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
