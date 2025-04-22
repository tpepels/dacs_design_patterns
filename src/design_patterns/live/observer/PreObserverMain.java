package design_patterns.live.observer;

// --- Display Components (Potential Observers) ---

/**
 * Displays current conditions. Tightly coupled to WeatherStation.
 */
class CurrentConditionsDisplay {
    private float temperature;
    private float humidity;

    // Method called DIRECTLY by WeatherStation
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
 * Displays weather statistics. Tightly coupled to WeatherStation.
 */
class StatisticsDisplay {
    // In a real scenario, would track min/max/avg
    private float lastTemperature;

    // Method called DIRECTLY by WeatherStation
    public void update(float temperature, float humidity) {
        this.lastTemperature = temperature;
        // Simulate complex stats update
        display();
    }

    public void display() {
        System.out.println("[Statistics Display] Using last temp for stats: " + lastTemperature + "F");
        // Real logic: Avg/Max/Min temperature: xxx/yyy/zzz
    }
}

/**
 * Displays a weather forecast. Tightly coupled to WeatherStation.
 */
class ForecastDisplay {
    private float currentHumidity;
    private float lastHumidity = -1;

    // Method called DIRECTLY by WeatherStation
    public void update(float temperature, float humidity) {
        lastHumidity = currentHumidity;
        currentHumidity = humidity;
        display();
    }

    public void display() {
        System.out.print("[Forecast Display] Forecast: ");
        if (currentHumidity > lastHumidity && lastHumidity != -1) {
            System.out.println("Improving weather on the way!");
        } else if (currentHumidity == lastHumidity && lastHumidity != -1) {
            System.out.println("More of the same");
        } else if (currentHumidity < lastHumidity && lastHumidity != -1) {
            System.out.println("Watch out for cooler, rainy weather");
        } else {
            System.out.println("Need more data for forecast");
        }
    }
}

// --- The "Subject" ---

/**
 * Knows about concrete "Display" classes. Problematic coupling.
 */
class WeatherStation {
    private float temperature;
    private float humidity;

    // *** PROBLEM AREA START ***
    // Direct references to the concrete display objects.
    private CurrentConditionsDisplay currentDisplay;
    private StatisticsDisplay statsDisplay;
    private ForecastDisplay forecastDisplay;
    // If we add a new display type (e.g., HeatIndexDisplay), we need to add a field
    // here.

    // Constructor or setters needed to link displays
    public WeatherStation(CurrentConditionsDisplay current, StatisticsDisplay stats, ForecastDisplay forecast) {
        this.currentDisplay = current;
        this.statsDisplay = stats;
        this.forecastDisplay = forecast;
    }
    // Alternative: Setters
    // public void setCurrentDisplay(CurrentConditionsDisplay d) {
    // this.currentDisplay = d; }
    // public void setStatsDisplay(StatisticsDisplay d) { this.statsDisplay = d; }
    // public void setForecastDisplay(ForecastDisplay d) { this.forecastDisplay = d;
    // }

    // Called when measurements change
    public void measurementsChanged() {
        // Get current values (simulated)
        // In real life, read from sensors

        System.out.println("\nWeather Station: New measurements available!");

        // *** PROBLEM AREA CONTINUES ***
        // Explicitly calling update methods on concrete display objects.
        // If we add HeatIndexDisplay, we MUST modify this method.
        if (currentDisplay != null) {
            currentDisplay.update(temperature, humidity);
        }
        if (statsDisplay != null) {
            statsDisplay.update(temperature, humidity);
        }
        if (forecastDisplay != null) {
            forecastDisplay.update(temperature, humidity);
        }
        // if (heatIndexDisplay != null) { heatIndexDisplay.update(...); } //
        // Modification needed for new display
        // *** PROBLEM AREA END ***
    }

    // Simulate getting new sensor readings
    public void setMeasurements(float temperature, float humidity) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Weather Station: Setting measurements to " + temperature + "F, " + humidity + "%");
        this.temperature = temperature;
        this.humidity = humidity;
        measurementsChanged(); // Notify displays
    }

    // Getters might be needed by displays if they pulled data, but here we push
    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }
}

// --- Main Application / Usage ---
public class PreObserverMain {
    public static void main(String[] args) {
        // 1. Create the concrete display objects
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statsDisplay = new StatisticsDisplay();
        ForecastDisplay forecastDisplay = new ForecastDisplay();
        // Imagine a new HeatIndexDisplay heatIndexDisplay = new HeatIndexDisplay();

        // 2. Create the WeatherStation and *pass the specific displays* to it.
        // This creates the tight coupling.
        WeatherStation weatherStation = new WeatherStation(currentDisplay, statsDisplay, forecastDisplay);
        // If we added HeatIndexDisplay, we'd likely need a new constructor or a setter
        // call:
        // weatherStation.setHeatIndexDisplay(heatIndexDisplay);

        // 3. Simulate new weather measurements
        weatherStation.setMeasurements(80, 65);
        weatherStation.setMeasurements(82, 70); // Humidity increased
        weatherStation.setMeasurements(78, 68); // Temp dropped, humidity dropped slightly
    }
}