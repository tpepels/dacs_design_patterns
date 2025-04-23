package design_patterns.lecture.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// --- Cart Item Interface and Concrete Items ---

interface CartItem {
    String getName();

    double getPrice();

    double getWeight(); // For shipping calculations
    // Add specific methods if needed, e.g., requiresInsurance() for electronics
}

class BookItem implements CartItem {
    private String name;
    private double price;
    private double weight;
    private String isbn;

    public BookItem(String name, double price, double weight, String isbn) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.isbn = isbn;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public String getIsbn() {
        return isbn;
    }
}

class ElectronicsItem implements CartItem {
    private String name;
    private double price;
    private double weight;
    private boolean requiresExtraInsurance;

    public ElectronicsItem(String name, double price, double weight, boolean insurance) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.requiresExtraInsurance = insurance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public boolean isRequiresExtraInsurance() {
        return requiresExtraInsurance;
    }
}

class GroceryItem implements CartItem {
    private String name;
    private double price;
    private double weight; // Weight matters for shipping
    private boolean isTaxExempt; // Tax rule specific to groceries

    public GroceryItem(String name, double price, double weight, boolean taxExempt) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.isTaxExempt = taxExempt;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public boolean isTaxExempt() {
        return isTaxExempt;
    }
}

// --- Shopping Cart (Context containing the problematic logic) ---
class ShoppingCart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        items.add(item);
    }

    // *** PROBLEM AREA START ***
    // Method for calculating shipping. Logic is mixed based on item type.
    public double calculateTotalShipping() {
        System.out.println("\n--- Calculating Shipping (Pre-Visitor) ---");
        double totalShippingCost = 0;
        double baseRatePerKg = 1.5;
        double electronicsInsuranceFee = 5.0;

        for (CartItem item : items) {
            double itemShipping = item.getWeight() * baseRatePerKg;

            // PROBLEM 1: Type checking and casting. Error-prone.
            // PROBLEM 2: Logic for shipping is embedded here.
            // PROBLEM 3: OCP Violation - adding a new item type (e.g., Furniture)
            // requires modifying THIS method.
            if (item instanceof ElectronicsItem) {
                ElectronicsItem electronics = (ElectronicsItem) item;
                if (electronics.isRequiresExtraInsurance()) {
                    itemShipping += electronicsInsuranceFee;
                    System.out.println("  + Adding $" + electronicsInsuranceFee + " insurance for " + item.getName());
                }
            } else if (item instanceof BookItem) {
                // Books might have a slightly lower base rate? (Example complexity)
                // itemShipping = item.getWeight() * (baseRatePerKg - 0.2); // Just an example
                System.out.println("  Calculating standard shipping for Book: " + item.getName());
            } else if (item instanceof GroceryItem) {
                // Groceries might have special handling fees if heavy? (Example complexity)
                System.out.println("  Calculating standard shipping for Grocery: " + item.getName());
            }
            // To add Furniture, add another 'else if' HERE

            totalShippingCost += itemShipping;
        }
        System.out.println("Total Shipping Cost: $" + String.format("%.2f", totalShippingCost));
        return totalShippingCost;
    }

    // Method for calculating tax. Similar problems.
    public double calculateTotalTax() {
        System.out.println("\n--- Calculating Tax (Pre-Visitor) ---");
        double totalTax = 0;
        double standardTaxRate = 0.08; // 8%

        for (CartItem item : items) {
            double itemTax = 0;

            // PROBLEM: More type checking and specific logic mixed in.
            if (item instanceof GroceryItem) {
                GroceryItem grocery = (GroceryItem) item;
                if (grocery.isTaxExempt()) {
                    itemTax = 0; // Tax-free
                    System.out.println("  " + item.getName() + " is tax exempt.");
                } else {
                    itemTax = item.getPrice() * standardTaxRate;
                }
            } else if (item instanceof BookItem) {
                // Maybe books have a lower tax rate?
                itemTax = item.getPrice() * (standardTaxRate - 0.03); // 5% for books
                System.out.println("  Applying special book tax rate for: " + item.getName());
            } else {
                // Standard rate for Electronics and anything else
                itemTax = item.getPrice() * standardTaxRate;
                System.out.println("  Applying standard tax rate for: " + item.getName());
            }
            // To add Furniture (maybe different rate), add another 'else if' HERE

            totalTax += itemTax;
        }
        System.out.println("Total Tax: $" + String.format("%.2f", totalTax));
        return totalTax;
    }

    // Method for generating packing list. Similar problems.
    public String generatePackingList() {
        System.out.println("\n--- Generating Packing List (Pre-Visitor) ---");
        StringBuilder packingList = new StringBuilder("Packing List:\n");
        for (CartItem item : items) {
            packingList.append("- ").append(item.getName());

            // PROBLEM: More type-specific logic embedded here.
            if (item instanceof ElectronicsItem) {
                packingList.append(" [PACKING: Use bubble wrap and anti-static bag]");
            } else if (item instanceof BookItem) {
                packingList.append(" [PACKING: Standard box]");
            } else if (item instanceof GroceryItem) {
                packingList.append(" [PACKING: Check for temperature sensitivity]");
            }
            // To add Furniture (heavy lift note), add another 'else if' HERE

            packingList.append("\n");
        }
        System.out.println(packingList.toString());
        return packingList.toString();
    }
    // PROBLEM: Adding a new operation (e.g., calculateLoyaltyPoints) requires
    // adding ANOTHER method here with similar complex conditional logic.
    // *** PROBLEM AREA END ***
}

// --- Main Application / Usage (Illustrative, not in UML) ---
public class PreVisitorCartMain {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new BookItem("Design Patterns Explained", 35.00, 0.9, "978-123456"));
        cart.addItem(new ElectronicsItem("Wireless Mouse", 25.00, 0.2, false));
        cart.addItem(new ElectronicsItem("Laptop", 1200.00, 2.5, true)); // Requires insurance
        cart.addItem(new GroceryItem("Organic Apples", 5.50, 1.0, true)); // Tax exempt
        cart.addItem(new GroceryItem("Imported Cheese", 15.00, 0.5, false)); // Taxable

        // Perform operations - logic is inside ShoppingCart methods
        cart.calculateTotalShipping();
        cart.calculateTotalTax();
        cart.generatePackingList();

        // Adding a new operation (e.g., calculate insurance cost report)
        // would require adding a new method to ShoppingCart with more instanceof
        // checks.
    }
}
