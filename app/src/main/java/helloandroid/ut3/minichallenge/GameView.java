package helloandroid.ut3.minichallenge;

import static helloandroid.ut3.minichallenge.utils.FormesUtils.stickmanTouchCircle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import helloandroid.ut3.minichallenge.capteurs.SensorListenerCallback;
import helloandroid.ut3.minichallenge.capteurs.SensorManagerClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorListenerCallback {
    private GameThread thread;
    private Timer activeTimer;
    private int screenWidth;
    private int screenHeight;
    private int centerWidth;
    private int centerHeigth;
    private int circleRadius = 50; // Rayon de la boule
    private int circleCenterX;
    private int circleCenterY;
    private int movementX = 0;
    private int movementY = 0;
    private List<Stickman> stickmanList;
    private boolean isDark = false; // False si light on
    private int score = 0;
    private Context context;

    private int[] nbStickmanVague = {5,10,20,40,60,100,200,400,800};
    private int nbvague = 0;
    private int nbstickmanSend;
    private Bitmap background;
    private int canvaWidth;
    private int canvaHeigth;
    private Bitmap scaledBackground;

    public GameView(Context context) {
        super(context);
        this.context = context;
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
        getHolder().addCallback(this);

        stickmanList = new ArrayList<>();

        SensorManagerClass sensorManager = new SensorManagerClass(context, this);
        sensorManager.registerListener();

        activeTimer = createTimer(1500);

        canvaHeigth = 0;
        canvaWidth = 0;

        // Image en arrière-plan
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
    }

    private Timer createTimer(int time){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                sendStickman();
            }
        };

        timer.scheduleAtFixedRate(timerTask, time,time);
        return timer;
    }

    public void updateBackground(){
        scaledBackground = Bitmap.createScaledBitmap(background, canvaWidth, canvaHeigth, true);
    }

    private void sendStickman(){
        if(nbstickmanSend != nbStickmanVague[nbvague]) {
            stickmanList.add(new Stickman(screenWidth, screenHeight));
            nbstickmanSend++;
        } else if(stickmanList.size() == 0) {
            nbvague++;
            nbstickmanSend = 0;

            int time = 1500 - (200*nbvague);
            if(time <= 0){
                time = 100;
            }
            //New time for timer
            activeTimer.cancel();
            activeTimer = createTimer(time);
        }
    }

    public void updateBoule() {
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

        for(Stickman st : stickmanList){
            if(stickmanTouchCircle(st, circleCenterX, circleCenterY, circleRadius)){
                score += 3;
                stickmanList.remove(st);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Centre de l'écran
        centerHeigth = getRootView().getHeight() / 2;
        centerWidth = getRootView().getWidth() / 2;

        if (canvas != null) {
            if (canvaWidth == 0 && canvaHeigth == 0){
                canvaWidth = canvas.getWidth();
                canvaHeigth = canvas.getHeight();

                updateBackground();
            }

            //Gestion de la couleur du canva en fonction de la luminosité
            if (isDark) {
                // Dessiner l'image d'arrière-plan sur tout le canvas
                canvas.drawBitmap(scaledBackground, 0, 0, null);
            }
            else
                canvas.drawColor(Color.BLACK);

            // Score
            Paint colorText = new Paint();
            colorText.setTextSize(100);
            colorText.setColor(Color.GRAY);
            canvas.drawText(String.valueOf(score), 50, 100, colorText);

            // Vague
            canvas.drawText("Vague : "+String.valueOf(nbvague+1), centerWidth, 100, colorText);

            // Zone de destruction
            Paint paintDestruction = new Paint();
            paintDestruction.setColor(Color.YELLOW);
            paintDestruction.setStyle(Paint.Style.STROKE); // Style du contour du cercle
            paintDestruction.setStrokeWidth(5); // Épaisseur
            canvas.drawCircle(centerWidth, centerHeigth, 300, paintDestruction);


            // Charger l'image à partir des ressources
            Bitmap originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.graal);
            Bitmap resizedImage = Bitmap.createScaledBitmap(originalImage, 100, 100, false);

            // Définir le dégradé radial pour le halo
            RadialGradient gradient = new RadialGradient(centerWidth, centerHeigth, Math.max(resizedImage.getWidth(), resizedImage.getHeight()) * 0.75f,
                    new int[]{Color.YELLOW, Color.TRANSPARENT}, null, Shader.TileMode.CLAMP);

            // Appliquer le dégradé au halo
            Paint haloPaint = new Paint();
            haloPaint.setShader(gradient);

            // Dessiner le halo lumineux autour de l'image
            canvas.drawCircle(centerWidth, centerHeigth, Math.max(resizedImage.getWidth(), resizedImage.getHeight()) * 0.75f, haloPaint);

            // Affichage de l'image du Graal
            canvas.drawBitmap(resizedImage, centerWidth - resizedImage.getWidth() / 2, centerHeigth - resizedImage.getHeight() / 2, null);

            // Boule qui bouge
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, paint);

            paintStickman(canvas);

            updateBoule();
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
        this.movementX = (int) accValue[1] * 2;
        this.movementY = (int) accValue[0] * 2;
    }

    public void paintStickman(Canvas canvas) {
        for(Stickman stickman : stickmanList) {
            stickman.update(this.context, screenWidth/2, screenHeight/2, centerWidth, centerHeigth, isDark);
            if(stickman.isInProtectedZone(screenWidth/2, screenHeight/2, circleRadius)) {
                thread.setRunning(false);
                Intent intent = new Intent(context, EndActivity.class);
                intent.putExtra("score", String.valueOf(score));
                context.startActivity(intent);
            }
            if (isDark)
                canvas.drawRect(stickman.getStickman(), stickman.getPaint());
            else {
                canvas.drawRect(stickman.getStickman(), new Paint(Color.BLACK));
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (Stickman stickman : stickmanList) {
                    if (stickman.isDestructible() && stickman.getStickman().contains(touchX, touchY)) {
                        score++;
                        stickmanList.remove(stickman);
                        break;
                    }
                }
                invalidate();
                break;
        }
        return true;
    }
}
