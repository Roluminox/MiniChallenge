package helloandroid.ut3.minichallenge.utils;
import helloandroid.ut3.minichallenge.Stickman;

public class FormesUtils {
    // Collision entre un ennemi et la boule
    public static boolean stickmanTouchCircle(Stickman stickman, int circleCenterX, int circleCenterY, int circleRadius) {
        return(
                stickman.getX()+25 < circleCenterX + circleRadius && stickman.getX()+25 > circleCenterX - circleRadius &&
                        stickman.getY()+25 < circleCenterY + circleRadius && stickman.getY()+25 > circleCenterY - circleRadius
        );
    }
}