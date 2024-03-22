package helloandroid.ut3.minichallenge.objects;

import android.graphics.Color;
import android.graphics.Paint;

public class Obstacle extends Object {

    Paint color;

    public Obstacle(float left, float top, float sizeWidth, float sizeHeight, Color color) {
        super(left, top, sizeWidth, sizeHeight);
        this.color = new Paint();
        this.color.setColor(Color.GREEN);
    }

    public Paint getColor() {
        return this.color;
    }
}
