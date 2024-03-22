package helloandroid.ut3.minichallenge.objects;

import android.graphics.RectF;

public class Object {

    private RectF bounds;

    public Object(float left, float top, float sizeWidth, float sizeHeight) {
        bounds = new RectF(left, top, left+sizeWidth, top+sizeHeight);
    }

    public RectF getBounds() {
        return bounds;
    }

    public boolean collidesWith(RectF rect) {
        return bounds.intersect(rect);
    }
}
