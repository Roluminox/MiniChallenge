package helloandroid.ut3.minichallenge.utils;

import android.graphics.Path;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import helloandroid.ut3.minichallenge.Stickman;

public class FormesUtils {

    public static boolean stickmanTouchCircle(Stickman stickman, int circleCenterX, int circleCenterY, int circleRadius) {
        return(
                stickman.getX()+25 < circleCenterX + circleRadius && stickman.getX()+25 > circleCenterX - circleRadius &&
                        stickman.getY()+25 < circleCenterY + circleRadius && stickman.getY()+25 > circleCenterY - circleRadius
        );
    }
}