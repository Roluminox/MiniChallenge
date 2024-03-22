package helloandroid.ut3.minichallenge.capteurs;

import android.hardware.SensorEventListener;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorManagerClass implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accSensor;
    private SensorListenerCallback callback;

    public SensorManagerClass(Context context, SensorListenerCallback callback) {
        this.callback = callback;
        // Initialise le gestionnaire de capteur
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // Récupère une référence au capteur de luminosité
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void registerListener() {
        // Enregistre l'écouteur de capteur
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterListener() {
        // Désenregistre l'écouteur de capteur
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lux = event.values[0];
            // L'envoie dans photo
            callback.onLuxValueChange(lux);
        } else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            Log.e("eee",String.valueOf(event.values[0]));
            float acc = event.values[0];
            callback.onAccValueChange(acc);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}