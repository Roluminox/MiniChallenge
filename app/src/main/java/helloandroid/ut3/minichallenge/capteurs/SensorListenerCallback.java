package helloandroid.ut3.minichallenge.capteurs;

public interface SensorListenerCallback {
    void onLuxValueChange(float luxValue);
    void onAccValueChange(double[] accValue);

}