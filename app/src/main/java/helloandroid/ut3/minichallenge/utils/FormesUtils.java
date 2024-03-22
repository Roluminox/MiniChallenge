package helloandroid.ut3.minichallenge.utils;
import helloandroid.ut3.minichallenge.Stickman;

public class FormesUtils {
    // Collision entre un ennemi et la boule
    public static boolean stickmanTouchCircle(Stickman stickman, int circleCenterX, int circleCenterY, int distanceMin) {
        float distance = (Math.max(stickman.getX()+50, circleCenterX) - Math.min(stickman.getX()+50, circleCenterX)) + (Math.max(stickman.getY()+50, circleCenterY) - Math.min(stickman.getY()+50, circleCenterY));
        return distance < distanceMin;
    }
    public static boolean touchStickman(Stickman stickman, float touchX, float touchY) {
        return touchX >= stickman.getX() && touchX < stickman.getX() + stickman.getCharacter().getWidth() &&
                touchY >= stickman.getY() && touchY < stickman.getY() + stickman.getCharacter().getHeight();
    }
}