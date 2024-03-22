package helloandroid.ut3.minichallenge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import helloandroid.ut3.minichallenge.capteurs.SensorListenerCallback;
import helloandroid.ut3.minichallenge.capteurs.SensorManagerClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorListenerCallback {
    private GameThread thread;
    private Timer timer;
    private int screenWidth;
    private int screenHeight;
    private final int circleRadius = 30; // Rayon de la boule
    private int circleCenterX;
    private int circleCenterY;
    private int movementX = 0;
    private int movementY = 0;
    private List<Stickman> stickmanList;
    private int maxStickman;

    private boolean isDark = false; // False si light on

    public GameView(Context context) {
        super(context);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
        getHolder().addCallback(this);

        stickmanList = new ArrayList<>();
        maxStickman = 10;

        SensorManagerClass sensorManager = new SensorManagerClass(context, this);
        sensorManager.registerListener();

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                addStickman();
            }
        };

        // Déclenchement après 2 secondes (2000 millisecondes)
        timer.scheduleAtFixedRate(task, 2000,2000);
    }

    public void update() {
        // Mettre à jour les coordonnées du cercle pour le déplacer
        if(
                movementX > 0 && (circleCenterX + circleRadius) + movementX <= screenWidth ||
                movementX < 0 && (circleCenterX - circleRadius) + movementX >= 0
        ){
            circleCenterX += movementX;
        }

        if(
                movementY > 0 && (circleCenterY + circleRadius) + movementY <= screenHeight ||
                movementY < 0 && (circleCenterY - circleRadius) + movementY >= 0
        ) {
            circleCenterY += movementY;
        }
    }

    @Override
    public void draw(Canvas canvas) {

        // Centre de l'écran
        int centerHeigth = getRootView().getHeight() / 2;
        int centerWidth = getRootView().getWidth() / 2;

        super.draw(canvas);
        if (canvas != null) {
            //Gestion de la couleur du canva en fonction de la luminosité
            if (isDark)
                canvas.drawColor(Color.WHITE);
            else
                canvas.drawColor(Color.BLACK);

            // Zone de destruction
            Paint paintDestruction = new Paint();
            paintDestruction.setColor(Color.YELLOW);
            canvas.drawCircle(centerWidth, centerHeigth, 400, paintDestruction);

            // Zone à protéger
            Paint paintZone = new Paint();
            paintZone.setColor(Color.GREEN);
            paintZone.setStyle(Paint.Style.STROKE); // Style du contour du cercle
            paintZone.setStrokeWidth(5); // Épaisseur
            canvas.drawCircle(centerWidth, centerHeigth, 100, paintZone);

            // Boule qui bouge
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, paint);

            paintStickman(canvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
        // Initialiser les coordonnées du centre du cercle au centre de l'écran
        circleCenterX = screenWidth / 2;
        circleCenterY = screenHeight / 2;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void onLuxValueChange(float luxValue) {
        this.isDark = (luxValue > 10);
    }

    @Override
    public void onAccValueChange(double[] accValue) {
        this.movementX = (int) accValue[1];
        this.movementY = (int) accValue[0];
    }

    public void paintStickman(Canvas canvas) {
        if(stickmanList.size() < maxStickman) {
            addStickman();
        }

        for(Stickman stickman : stickmanList) {
            stickman.update(screenWidth/2, screenHeight/2);
            canvas.drawRect(stickman.getStickman(), stickman.getPaint());
        }
    }

    public void addStickman() {
        Random random = new Random();
        // Générer un nombre aléatoire entre 0 et 3
        int randomCoin = random.nextInt(4);

        Stickman newStickman = null;
        switch(randomCoin) {
            // Haut
            case 0:
                int randomTop = random.nextInt(screenWidth);
                newStickman = new Stickman(randomTop, 0);
                break;
            // Droite
            case 1:
                int randomRight = random.nextInt(screenHeight);
                newStickman = new Stickman(screenWidth-50, randomRight);
                break;
            // Gauche
            case 2:
                int randomLeft = random.nextInt(screenHeight);
                newStickman = new Stickman(0, randomLeft);
                break;
            // Bas
            case 3:
                int randomBottom = random.nextInt(screenWidth);
                newStickman = new Stickman(randomBottom, screenHeight-50);
        }

        stickmanList.add(newStickman);
    }
}
