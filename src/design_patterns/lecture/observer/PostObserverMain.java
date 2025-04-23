package design_patterns.lecture.observer;

import java.util.ArrayList;
import java.util.List;

// --- Observer Pattern Interfaces ---

/**
 * The Observer interface is implemented by all observers, so they can receive
 * updates.
 */
interface Observer {
    // Receive update from subject. Could pass subject reference or state directly.
    void update(float temperature, float humidity);
}

/**
 * The Subject interface is implemented by the object being observed.
 */
interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers(); // Notify all registered observers
}

// --- Concrete Subject ---

/**
 * Implements the Subject interface. Maintains state and notifies observers.
 */
class WeatherStationRefactored implements Subject {
    private List<Observer> observers; // Holds registered observers
    private float temperature;
    private float humidity;

    public WeatherStationRefactored() {
        observers = new ArrayList<>(); // Initialize the list
    }

    @Override
    public void registerObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            System.out.println("Weather Station: Registering observer " + o.getClass().getSimpleName());
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        if (observers.remove(o)) {
            System.out.println("Weather Station: Removing observer " + o.getClass().getSimpleName());
        }
    }

    @Override
    public void notifyObservers() {
        System.out.println("Weather Station: Notifying " + observers.size() + " observers...");
        // Use enhanced for-loop for safety if observers could be removed during
        // notification
        // Or create a copy: List<Observer> observersCopy = new ArrayList<>(observers);
        for (Observer observer : observers) {
            observer.update(temperature, humidity); // Call the standard update method
        }
    }

    // Called when measurements change
    public void measurementsChanged() {
        System.out.println("\nWeather Station: New measurements available!");
        notifyObservers(); // The key change: just notify, don't call specific methods
    }

    // Simulate getting new sensor readings
    public void setMeasurements(float temperature, float humidity) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Weather Station: Setting measurements to " + temperature + "F, " + humidity + "%");
        this.temperature = temperature;
        this.humidity = humidity;
        measurementsChanged(); // Trigger notification
    }

    // Getters might be used if Observers "pull" data instead of "push"
    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }
}

// --- Concrete Observers ---

/**
 * Implements Observer so it can get updates from the WeatherStation.
 * No longer tightly coupled.
 */
class CurrentConditionsDisplayRefactored implements Observer {
    private float temperature;
    private float humidity;
    // Could optionally hold a reference to the Subject to pull data or deregister
    // private Subject weatherStation;

    // Constructor could take Subject to register itself, or registration is done
    // externally
    public CurrentConditionsDisplayRefactored(/* Subject weatherStation */) {
        // this.weatherStation = weatherStation;
        // weatherStation.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display() {
        System.out.println("[Current Conditions Display] Current conditions: " + temperature
                + "F degrees and " + humidity + "% humidity");
    }
}

/**
 * Implements Observer. No longer tightly coupled.
 */
class StatisticsDisplayRefactored implements Observer {
    private float lastTemperature;

    @Override
    public void update(float temperature, float humidity) {
        this.lastTemperature = temperature;
        display();
    }

    public void display() {
        System.out.println("[Statistics Display] Using last temp for stats: " + lastTemperature + "F");
    }
}

/**
 * Implements Observer. No longer tightly coupled.
 */
class ForecastDisplayRefactored implements Observer {
    private float currentHumidity;
    private float lastHumidity = -1; // Initialize to indicate no previous data

    @Override
    public void update(float temperature, float humidity) {
        lastHumidity = currentHumidity; // Store previous value before updating
        currentHumidity = humidity; // Update with new value
        display();
    }

    public void display() {
        System.out.print("[Forecast Display] Forecast: ");
        // Ensure lastHumidity has a valid value before comparing
        if (lastHumidity != -1) {
            if (currentHumidity > lastHumidity) {
                System.out.println("Improving weather on the way!");
            } else if (currentHumidity == lastHumidity) {
                System.out.println("More of the same");
            } else { // currentHumidity < lastHumidity
                System.out.println("Watch out for cooler, rainy weather");
            }
        } else {
            // Handle the first update case
            System.out.println("Need more data for forecast trend (initial reading: " + currentHumidity + "%)");
            // Set lastHumidity for the *next* comparison if needed, or rely on update
            // sequence.
            // Setting it here might be confusing, better to let the next update establish
            // the trend.
            // lastHumidity = currentHumidity; // Or keep as -1 until second update
        }
    }
}

// --- NEW Observer Example ---
/**
 * A new observer type we can add WITHOUT changing WeatherStationRefactored.
 */
class HeatIndexDisplay implements Observer {
    private float heatIndex = 0.0f;

    @Override
    public void update(float temperature, float humidity) {
        // Simple heat index calculation (approximation)
        heatIndex = (float) (-42.379 + 2.04901523 * temperature + 10.14333127 * humidity
                - .22475541 * temperature * humidity - .00683783 * temperature * temperature
                - .05481717 * humidity * humidity + .00122874 * temperature * temperature * humidity
                + .00085282 * temperature * humidity * humidity
                - .00000199 * temperature * temperature * humidity * humidity);
        display();
    }

    public void display() {
        System.out.println("[Heat Index Display] Heat index is " + String.format("%.2f", heatIndex));
    }
}

// --- Main Application / Usage (Using Observer Pattern) ---
public class PostObserverMain {
    public static void main(String[] args) {
        // 1. Create the Subject
        WeatherStationRefactored weatherStation = new WeatherStationRefactored();

        // 2. Create the Observers
        Observer currentDisplay = new CurrentConditionsDisplayRefactored();
        Observer statsDisplay = new StatisticsDisplayRefactored();
        Observer forecastDisplay = new ForecastDisplayRefactored();
        Observer heatIndexDisplay = new HeatIndexDisplay(); // Create the new observer

        // 3. Register observers with the subject
        weatherStation.registerObserver(currentDisplay);
        weatherStation.registerObserver(statsDisplay);
        weatherStation.registerObserver(forecastDisplay);
        // We can add the new observer WITHOUT changing the WeatherStation class!
        weatherStation.registerObserver(heatIndexDisplay);

        // 4. Simulate new weather measurements - triggers notifications
        weatherStation.setMeasurements(80, 65);
        weatherStation.setMeasurements(82, 70);

        // 5. Demonstrate removing an observer
        System.out.println("\n>> Removing Forecast Display <<\n");
        weatherStation.removeObserver(forecastDisplay);
        weatherStation.setMeasurements(78, 68); // Forecast display will not update

        // 6. Demonstrate removing another observer
        System.out.println("\n>> Removing Statistics Display <<\n");
        weatherStation.removeObserver(statsDisplay);
        weatherStation.setMeasurements(90, 75); // Only CurrentConditions and HeatIndex should update
    }
}
