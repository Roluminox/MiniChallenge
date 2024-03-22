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
    private Sensor accelerometreSensor;
    private SensorListenerCallback callback;

    public SensorManagerClass(Context context, SensorListenerCallback callback) {
        this.callback = callback;
        // Initialise le gestionnaire de capteur
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // Récupère une référence au capteur de luminosité
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelerometreSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void registerListener() {
        // Enregistre l'écouteur de capteur
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, accelerometreSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
            callback.onLuxValueChange(lux);
        } else {
            float[] gravity = event.values;

            double roll = Math.atan2(gravity[1], gravity[2]) * (180 / Math.PI);
            double pitch = Math.atan2(-gravity[0], Math.sqrt(gravity[1] * gravity[1] + gravity[2] * gravity[2])) * (180 / Math.PI);

            double[] retour = {roll, pitch};
            callback.onAccValueChange(retour);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}