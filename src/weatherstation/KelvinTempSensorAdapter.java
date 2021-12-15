package weatherstation;

import java.util.Random ;   // to simulate random fluctuations.

public class KelvinTempSensorAdapter implements ITempSensor{
    private KelvinTempSensor kts = new KelvinTempSensor();
    private double KTOC = -27315;

    @Override
    public double tempReading() {
        return (kts.reading() + KTOC) / 100;
    }
}
