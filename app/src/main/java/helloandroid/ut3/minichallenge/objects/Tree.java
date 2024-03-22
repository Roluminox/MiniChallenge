package helloandroid.ut3.minichallenge.objects;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

public class Tree extends Obstacle {

    public Tree(float left, float top) {
        super(left, top, 50, 50,
                Color.valueOf(Color.rgb(109, 72, 16)));
    }
}
