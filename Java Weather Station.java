## 1. Java code — `WeatherStation.java`

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class WeatherStation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       JAVA WEATHER STATION");
        System.out.println("=================================");

        while (true) {

            try {
                // Read temperature
                double temperature = readTemperature();

                // Read humidity
                double humidity = readHumidity();

                System.out.println();
                System.out.println("-------------------------------");
                System.out.printf("Temperature : %.2f °C%n", temperature);
                System.out.printf("Humidity    : %.2f %% %n", humidity);
                System.out.println("-------------------------------");

                Thread.sleep(5000);

            } catch (Exception e) {
                System.out.println("Sensor error: " + e.getMessage());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /*
     * Reads temperature from Raspberry Pi.
     *
     * This example uses the Linux command `vcgencmd`
     * as a simple temperature source.
     */
    private static double readTemperature() throws IOException {

        Process process = Runtime.getRuntime().exec(
                "vcgencmd measure_temp"
        );

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(process.getInputStream())
                );

        String output = reader.readLine();

        if (output == null) {
            throw new IOException("Unable to read temperature");
        }

        // Example:
        // temp=45.8'C

        output = output.replace("temp=", "");
        output = output.replace("'C", "");

        return Double.parseDouble(output);
    }

    /*
     * Placeholder for humidity sensor.
     *
     * Replace this method with DHT11/DHT22 GPIO
     * reading code when the sensor library is connected.
     */
    private static double readHumidity() {

        // Example value for testing.
        return 60.0;
    }
}
```

## 2. Compile

On the Raspberry Pi:

```bash
javac WeatherStation.java
```

Run:

```bash
java WeatherStation
```

Example output:

```text
=================================
       JAVA WEATHER STATION
=================================

-------------------------------
Temperature : 45.80 °C
Humidity    : 60.00 %
-------------------------------

-------------------------------
Temperature : 46.10 °C
Humidity    : 59.50 %
-------------------------------
```

## 3. Hardware

For a basic version:

```text
Raspberry Pi
     |
     +---- DHT11/DHT22
     |       |
     |       +---- VCC
     |       +---- GND
     |       +---- DATA
     |
     +---- Java Program
```

The uploaded source only specifies **Java + Raspberry Pi** and the project name; it does not specify the sensor or GPIO library.

For a **real weather station**, the next version can add:

* DHT11/DHT22 → temperature + humidity
* BMP280 → pressure
* LDR → light intensity
* Rain sensor → rainfall detection
* Anemometer → wind speed
* LCD/OLED → live display
* Java GUI → weather dashboard
* SQLite → save readings
* CSV export → historical data
* Web dashboard → view readings from a phone
