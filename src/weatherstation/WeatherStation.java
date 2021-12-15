package weatherstation;
/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */
 
/*
 * Class for a simple computer based weather station that reports the current
 * temperature (in Celsius) every second. The station is attached to a
 * sensor that reports the temperature as a 16-bit number (0 to 65535)
 * representing the Kelvin temperature to the nearest 1/100th of a degree.
 *
 * This class is implements Runnable so that it can be embedded in a Thread
 * which runs the periodic sensing.
 *
 * The class also extends Observable so that it can notify registered
 * objects whenever its state changes. Convenience functions are provided
 * to access the temperature in different schemes (Celsius, Kelvin, etc.)
 */
import java.util.Observable ;

public class WeatherStation extends Observable implements Runnable {

    private final KelvinTempSensorAdapter sensor ; // Temperature sensor.
    private final Barometer mercury ;       // Barometer sensor


    private final long PERIOD = 1000 ;      // 1 sec = 1000 ms
    private final int KTOC = -27315 ;       // Kelvin to Celsius conversion.
    private final double ITOM = 33.864 ;       // Inches to Millibar conversion.

    private double currentReading ;
    private double currentMercuryReading ;
    private final IBarometer Ibar;
    private final ITempSensor ITemp;

    /*
     * When a WeatherStation object is created, it in turn creates the sensor
     * object it will use.
     */
    public WeatherStation(IBarometer Ibar, ITempSensor ITemp) {
        sensor = new KelvinTempSensorAdapter() ;
        currentReading = sensor.tempReading() ;

        mercury = new Barometer();
        currentMercuryReading = mercury.pressure();

        this.Ibar=Ibar;
        this.ITemp=ITemp;

    }

    /*
     * The "run" method called by the enclosing Thread object when started.
     * Repeatedly sleeps a second, acquires the current temperature from its
     * sensor, and notifies registered Observers of the change.
     */
    public void run() {
        while( true ) {
            try {
                Thread.sleep(PERIOD) ;
            } catch (Exception e) {}    // ignore exceptions

            /*
             * Get next reading and notify any Observers.
             */
            synchronized(this) {
                currentReading = sensor.tempReading() ;
                currentMercuryReading = mercury.pressure() ;
            }
            setChanged() ;
            notifyObservers() ;
        }
    }

    /*
     * Return the current reading in degrees celsius as a
     * double precision number.
     */
    public synchronized double getCelsius() {
        return currentReading;
    }

    /*
     * Return the current reading in degrees Kelvin as a
     * double precision number.
     */
    public synchronized double getKelvin() {
        return currentReading + 273.15 ;
    }

    public synchronized double getF() {
        return (currentReading * 1.8 + 32);
    }

    public synchronized double getInches() {
        return currentMercuryReading ;
    }

    public synchronized double getMbar() {
        return currentMercuryReading * ITOM;
    }


}