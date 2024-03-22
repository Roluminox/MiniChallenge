package helloandroid.ut3.minichallenge.utils;
import helloandroid.ut3.minichallenge.Stickman;

public class FormesUtils {
    // Collision entre un ennemi et la boule
    public static boolean stickmanTouchCircle(Stickman stickman, int circleCenterX, int circleCenterY, int circleRadius) {
        return(
                stickman.getX()+15 < circleCenterX + circleRadius && stickman.getX()+15 > circleCenterX - circleRadius &&
                        stickman.getY()+15 < circleCenterY + circleRadius && stickman.getY()+15 > circleCenterY - circleRadius
        );
    }
    public static boolean touchStickman(Stickman stickman, float touchX, float touchY) {
        return touchX >= stickman.getX() && touchX < stickman.getX() + stickman.getCharacter().getWidth() &&
                touchY >= stickman.getY() && touchY < stickman.getY() + stickman.getCharacter().getHeight();
    }
}