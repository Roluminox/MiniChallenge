package helloandroid.ut3.minichallenge.objects;

import android.graphics.Color;

public class Bush extends Obstacle {

    public Bush(float left, float top) {
        super(left, top, 50, 50,
                Color.valueOf(Color.rgb(0, 200, 0)));
    }
}
