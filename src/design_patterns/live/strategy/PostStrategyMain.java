package design_patterns.live.strategy;

import java.util.ArrayList;
import java.util.List;

// Item class remains the same
class Item {
    private String name;
    private double price;
    private double weight;

    public Item(String name, double price, double weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}

// --- Strategy Interface ---
/**
 * The Strategy interface declares operations common to all supported versions
 * of some algorithm. The Context uses this interface to call the algorithm
 * defined by Concrete Strategies.
 */
interface ShippingStrategy {
    double calculate(Order order); // Pass the context (Order) to the strategy
}

// --- Concrete Strategies ---
/**
 * Concrete Strategies implement the algorithm while following the base Strategy
 * interface.
 */
class FlatRateShippingStrategy implements ShippingStrategy {
    private final double rate;

    public FlatRateShippingStrategy(double rate) {
        this.rate = rate;
    }

    @Override
    public double calculate(Order order) {
        System.out.println("Applying Flat Rate shipping: $" + String.format("%.2f", rate));
        return rate;
    }
}

class WeightBasedShippingStrategy implements ShippingStrategy {
    private final double ratePerKg;

    public WeightBasedShippingStrategy(double ratePerKg) {
        this.ratePerKg = ratePerKg;
    }

    @Override
    public double calculate(Order order) {
        double totalWeight = order.getTotalWeight();
        double shippingCost = totalWeight * ratePerKg;
        System.out.println("Applying Weight-Based shipping (" + String.format("%.2f", totalWeight) +
                "kg * $" + String.format("%.2f", ratePerKg) + "/kg): $" + String.format("%.2f", shippingCost));
        return shippingCost;
    }
}

class FreeThresholdShippingStrategy implements ShippingStrategy {
    private final double threshold;
    private final double fallbackRate;

    public FreeThresholdShippingStrategy(double threshold, double fallbackRate) {
        this.threshold = threshold;
        this.fallbackRate = fallbackRate;
    }

    @Override
    public double calculate(Order order) {
        double totalValue = order.getTotalValue();
        double shippingCost;
        if (totalValue > threshold) {
            shippingCost = 0.00;
            System.out.println("Applying Free Threshold shipping (Value > $" + String.format("%.2f", threshold) +
                    "): $" + String.format("%.2f", shippingCost));
        } else {
            shippingCost = fallbackRate;
            System.out.println("Applying Threshold shipping (Value <= $" + String.format("%.2f", threshold) +
                    ", fallback rate): $" + String.format("%.2f", shippingCost));
        }
        return shippingCost;
    }
}

// --- Context Class (Refactored) ---
/**
 * The Context defines the interface of interest to clients. It maintains a
 * reference to one of the Strategy objects. The Context delegates some work
 * to the linked Strategy object instead of implementing multiple versions
 * of the algorithm on its own.
 */
class Order {
    private List<Item> items = new ArrayList<>();
    // Reference to the current strategy object.
    private ShippingStrategy shippingStrategy; // *** The key change ***

    public void addItem(Item item) {
        items.add(item);
    }

    // Setter to change the strategy at runtime
    public void setShippingStrategy(ShippingStrategy strategy) {
        this.shippingStrategy = strategy;
        System.out.println("\nOrder: Set shipping strategy to " + strategy.getClass().getSimpleName());
    }

    public double getTotalWeight() {
        return items.stream().mapToDouble(Item::getWeight).sum();
    }

    public double getTotalValue() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); // Return copy
    }

    // *** PROBLEM AREA REMOVED AND REPLACED ***
    // The Order class now delegates the calculation to the strategy object.
    public double getShippingCost() {
        if (shippingStrategy == null) {
            System.err.println("ERROR: Shipping strategy not set!");
            return 0.0; // Or throw exception
        }

        double totalValue = getTotalValue();
        double totalWeight = getTotalWeight();
        System.out.println("Calculating shipping for Order (Value: $" + String.format("%.2f", totalValue) +
                ", Weight: " + String.format("%.2f", totalWeight) + "kg) using " +
                shippingStrategy.getClass().getSimpleName());

        // Delegate the calculation to the current strategy object
        double cost = shippingStrategy.calculate(this); // Pass context (this Order)

        System.out.println("Total Shipping Cost: $" + String.format("%.2f", cost));
        return cost;
    }
}

// --- Main Application / Usage (Using Strategy) ---
public class PostStrategyMain {
    public static void main(String[] args) {
        // Create strategy instances (these could be singletons or configured elsewhere)
        ShippingStrategy flatRate = new FlatRateShippingStrategy(5.00);
        ShippingStrategy weightBased = new WeightBasedShippingStrategy(1.50); // $1.50/kg
        ShippingStrategy freeThreshold = new FreeThresholdShippingStrategy(50.00, 7.00); // Free > $50, else $7

        // --- Create Orders ---
        Order order1 = new Order();
        order1.addItem(new Item("Laptop", 1200.00, 2.5));
        order1.addItem(new Item("Mouse", 25.00, 0.2));

        Order order2 = new Order();
        order2.addItem(new Item("Book", 15.00, 0.8));
        order2.addItem(new Item("Keyboard", 75.00, 1.1)); // Total value > 50

        Order order3 = new Order();
        order3.addItem(new Item("Dumbbells", 45.00, 10.0)); // Heavy

        System.out.println("--- Processing Order 1 ---");
        order1.setShippingStrategy(freeThreshold); // Set strategy
        order1.getShippingCost(); // Use strategy

        order1.setShippingStrategy(weightBased); // Change strategy dynamically
        order1.getShippingCost();

        System.out.println("\n--- Processing Order 2 ---");
        order2.setShippingStrategy(freeThreshold); // Should be free
        order2.getShippingCost();

        order2.setShippingStrategy(flatRate); // Change strategy
        order2.getShippingCost();

        System.out.println("\n--- Processing Order 3 ---");
        order3.setShippingStrategy(weightBased); // Should be expensive
        order3.getShippingCost();

        // To add a NEW strategy (e.g., DestinationBased), we would:
        // 1. Create DestinationBasedShippingStrategy implements ShippingStrategy.
        // 2. Instantiate it: ShippingStrategy destinationBased = new
        // DestinationBasedShippingStrategy(...);
        // 3. Set it on an order: orderX.setShippingStrategy(destinationBased);
        // NO changes needed in the Order class itself!
    }
}
