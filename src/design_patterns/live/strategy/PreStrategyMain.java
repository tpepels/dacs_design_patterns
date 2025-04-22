package design_patterns.live.strategy;

import java.util.ArrayList;
import java.util.List;

// Simple Item class for the order
class Item {
    private String name;
    private double price;
    private double weight; // in kg

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

// The Context class initially containing the varying logic
class Order {
    private List<Item> items = new ArrayList<>();
    private String shippingMethod; // e.g., "FlatRate", "WeightBased", "FreeThreshold"

    public void addItem(Item item) {
        items.add(item);
    }

    public void setShippingMethod(String method) {
        this.shippingMethod = method;
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

    // *** PROBLEM AREA START ***
    // This method contains multiple algorithms (strategies) mixed together.
    public double calculateShippingCost() {
        if (shippingMethod == null || shippingMethod.isEmpty()) {
            System.err.println("ERROR: Shipping method not set!");
            return 0.0; // Or throw exception
        }

        double shippingCost = 0.0;
        double totalWeight = getTotalWeight();
        double totalValue = getTotalValue();

        System.out.println("\nCalculating shipping for Order (Total Value: $" + String.format("%.2f", totalValue) +
                ", Total Weight: " + String.format("%.2f", totalWeight) + "kg)");

        // PROBLEM 1: Conditional logic based on method type.
        // PROBLEM 2: Violates OCP - adding a new shipping method (e.g.,
        // DestinationBased)
        // requires modifying this method.
        // PROBLEM 3: Violates SRP - Order class manages items AND calculates shipping
        // costs.
        // PROBLEM 4: Becomes complex as more methods are added.
        switch (shippingMethod.toUpperCase()) {
            case "FLATRATE":
                shippingCost = 5.00; // Simple flat rate
                System.out.println("Applying Flat Rate shipping: $" + String.format("%.2f", shippingCost));
                break;
            case "WEIGHTBASED":
                // Example: $1.50 per kg
                shippingCost = totalWeight * 1.50;
                System.out.println("Applying Weight-Based shipping (" + String.format("%.2f", totalWeight)
                        + "kg * $1.50/kg): $" + String.format("%.2f", shippingCost));
                break;
            case "FREETHRESHOLD":
                // Example: Free shipping if order value > $50, otherwise $7.00 flat rate
                if (totalValue > 50.00) {
                    shippingCost = 0.00;
                    System.out.println("Applying Free Threshold shipping (Value > $50.00): $"
                            + String.format("%.2f", shippingCost));
                } else {
                    shippingCost = 7.00;
                    System.out.println("Applying Threshold shipping (Value <= $50.00, fallback rate): $"
                            + String.format("%.2f", shippingCost));
                }
                break;
            // To add a new method like "DestinationBased", add another case HERE.
            // case "DESTINATIONBASED":
            // shippingCost = calculateBasedOnDestination(...);
            // break;
            default:
                System.err.println("WARNING: Unknown shipping method '" + shippingMethod + "'. Defaulting to $0.00");
                shippingCost = 0.0; // Or throw exception
                break;
        }
        // *** PROBLEM AREA END ***

        System.out.println("Total Shipping Cost: $" + String.format("%.2f", shippingCost));
        return shippingCost;
    }
}

// --- Main Application / Usage ---
public class PreStrategyMain {
    public static void main(String[] args) {
        Order order1 = new Order();
        order1.addItem(new Item("Laptop", 1200.00, 2.5)); // High value, moderate weight
        order1.addItem(new Item("Mouse", 25.00, 0.2));

        Order order2 = new Order();
        order2.addItem(new Item("Book", 15.00, 0.8)); // Low value, low weight
        order2.addItem(new Item("Keyboard", 75.00, 1.1));

        Order order3 = new Order();
        order3.addItem(new Item("Dumbbells", 45.00, 10.0)); // Heavy, moderate value

        System.out.println("--- Processing Order 1 ---");
        order1.setShippingMethod("FreeThreshold"); // Should be free
        order1.calculateShippingCost();

        order1.setShippingMethod("WeightBased"); // Should be based on weight
        order1.calculateShippingCost();

        System.out.println("\n--- Processing Order 2 ---");
        order2.setShippingMethod("FreeThreshold"); // Should cost $7.00
        order2.calculateShippingCost();

        order2.setShippingMethod("FlatRate"); // Should cost $5.00
        order2.calculateShippingCost();

        System.out.println("\n--- Processing Order 3 ---");
        order3.setShippingMethod("WeightBased"); // Should be expensive due to weight
        order3.calculateShippingCost();

        order3.setShippingMethod("UnknownMethod"); // Test default case
        order3.calculateShippingCost();
    }
}