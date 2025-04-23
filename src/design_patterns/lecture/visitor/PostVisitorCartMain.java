package design_patterns.lecture.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// --- Visitor Interface ---
/**
 * Declares visit methods for each concrete CartItem type.
 */
interface CartItemVisitor {
    void visit(BookItem book);

    void visit(ElectronicsItem electronics);

    void visit(GroceryItem grocery);
    // Add visit(FurnitureItem furniture) if added later
}

// --- Cart Item Interface and Concrete Items (Modified) ---

interface CartItem {
    String getName();

    double getPrice();

    double getWeight();

    // The key method for the Visitor pattern
    void accept(CartItemVisitor visitor);
}

class BookItem implements CartItem {
    private String name;
    double price;
    double weight;
    String isbn;

    public BookItem(String n, double p, double w, String i) {
        name = n;
        price = p;
        weight = w;
        isbn = i;
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

    @Override
    public void accept(CartItemVisitor visitor) {
        visitor.visit(this); // Double dispatch: calls visitor's visit(BookItem)
    }
    // Calculation/packing logic REMOVED from here
}

class ElectronicsItem implements CartItem {
    private String name;
    double price;
    double weight;
    boolean requiresExtraInsurance;

    public ElectronicsItem(String n, double p, double w, boolean ins) {
        name = n;
        price = p;
        weight = w;
        requiresExtraInsurance = ins;
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

    @Override
    public void accept(CartItemVisitor visitor) {
        visitor.visit(this); // Calls visitor's visit(ElectronicsItem)
    }
    // Calculation/packing logic REMOVED from here
}

class GroceryItem implements CartItem {
    private String name;
    double price;
    double weight;
    boolean isTaxExempt;

    public GroceryItem(String n, double p, double w, boolean taxEx) {
        name = n;
        price = p;
        weight = w;
        isTaxExempt = taxEx;
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

    @Override
    public void accept(CartItemVisitor visitor) {
        visitor.visit(this); // Calls visitor's visit(GroceryItem)
    }
    // Calculation/packing logic REMOVED from here
}

// --- Concrete Visitors (Each encapsulates one operation) ---

class ShippingCostVisitor implements CartItemVisitor {
    private double totalCost = 0;
    private double baseRatePerKg = 1.5;
    private double electronicsInsuranceFee = 5.0;

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public void visit(BookItem book) {
        // Logic specific to Book shipping
        double itemShipping = book.getWeight() * baseRatePerKg;
        System.out
                .println("  Shipping cost for Book '" + book.getName() + "': $" + String.format("%.2f", itemShipping));
        totalCost += itemShipping;
    }

    @Override
    public void visit(ElectronicsItem electronics) {
        // Logic specific to Electronics shipping
        double itemShipping = electronics.getWeight() * baseRatePerKg;
        if (electronics.isRequiresExtraInsurance()) {
            itemShipping += electronicsInsuranceFee;
            System.out.println("  Shipping cost for Electronics '" + electronics.getName() + "' (incl. insurance): $"
                    + String.format("%.2f", itemShipping));
        } else {
            System.out.println("  Shipping cost for Electronics '" + electronics.getName() + "': $"
                    + String.format("%.2f", itemShipping));
        }
        totalCost += itemShipping;
    }

    @Override
    public void visit(GroceryItem grocery) {
        // Logic specific to Grocery shipping
        double itemShipping = grocery.getWeight() * baseRatePerKg;
        System.out.println(
                "  Shipping cost for Grocery '" + grocery.getName() + "': $" + String.format("%.2f", itemShipping));
        totalCost += itemShipping;
    }
}

class TaxCalculationVisitor implements CartItemVisitor {
    private double totalTax = 0;
    private final double standardTaxRate = 0.08;
    private final double bookTaxRate = 0.05;

    public double getTotalTax() {
        return totalTax;
    }

    @Override
    public void visit(BookItem book) {
        double itemTax = book.getPrice() * bookTaxRate;
        System.out.println("  Tax for Book '" + book.getName() + "' (at " + (bookTaxRate * 100) + "%): $"
                + String.format("%.2f", itemTax));
        totalTax += itemTax;
    }

    @Override
    public void visit(ElectronicsItem electronics) {
        double itemTax = electronics.getPrice() * standardTaxRate;
        System.out.println("  Tax for Electronics '" + electronics.getName() + "' (at " + (standardTaxRate * 100)
                + "%): $" + String.format("%.2f", itemTax));
        totalTax += itemTax;
    }

    @Override
    public void visit(GroceryItem grocery) {
        double itemTax = 0;
        if (grocery.isTaxExempt()) {
            System.out.println("  Tax for Grocery '" + grocery.getName() + "': $0.00 (Tax Exempt)");
        } else {
            itemTax = grocery.getPrice() * standardTaxRate;
            System.out.println("  Tax for Grocery '" + grocery.getName() + "' (at " + (standardTaxRate * 100) + "%): $"
                    + String.format("%.2f", itemTax));
        }
        totalTax += itemTax;
    }
}

class PackingListVisitor implements CartItemVisitor {
    private StringBuilder packingList = new StringBuilder();

    public String getPackingList() {
        return packingList.toString();
    }

    @Override
    public void visit(BookItem book) {
        packingList.append("- ").append(book.getName()).append(" [PACKING: Standard box]\n");
    }

    @Override
    public void visit(ElectronicsItem electronics) {
        packingList.append("- ").append(electronics.getName())
                .append(" [PACKING: Use bubble wrap and anti-static bag]\n");
    }

    @Override
    public void visit(GroceryItem grocery) {
        packingList.append("- ").append(grocery.getName()).append(" [PACKING: Check for temperature sensitivity]\n");
    }
}

// --- Shopping Cart (Context - Refactored and Simplified) ---
class ShoppingCart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        items.add(item);
    }

    // Single method to apply any visitor operation
    public void applyVisitor(CartItemVisitor visitor) {
        System.out.println("\n--- Applying Visitor: " + visitor.getClass().getSimpleName() + " ---");
        for (CartItem item : items) {
            item.accept(visitor); // Each item calls the correct visit method on the visitor
        }
    }

    // Specific calculation/generation methods are REMOVED from here.
    // public double calculateTotalShipping() { ... } // GONE
    // public double calculateTotalTax() { ... } // GONE
    // public String generatePackingList() { ... } // GONE
}

// --- Main Application / Usage (Illustrative, not in UML) ---
public class PostVisitorCartMain {
    public static void main(String[] args) {
        // 1. Create items (same as before)
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new BookItem("Design Patterns Explained", 35.00, 0.9, "978-123456"));
        cart.addItem(new ElectronicsItem("Wireless Mouse", 25.00, 0.2, false));
        cart.addItem(new ElectronicsItem("Laptop", 1200.00, 2.5, true));
        cart.addItem(new GroceryItem("Organic Apples", 5.50, 1.0, true));
        cart.addItem(new GroceryItem("Imported Cheese", 15.00, 0.5, false));

        // 2. Create Visitor instances for the operations
        ShippingCostVisitor shippingVisitor = new ShippingCostVisitor();
        TaxCalculationVisitor taxVisitor = new TaxCalculationVisitor();
        PackingListVisitor packingVisitor = new PackingListVisitor();

        // 3. Apply visitors to the cart
        cart.applyVisitor(shippingVisitor);
        double totalShipping = shippingVisitor.getTotalCost();
        System.out.println(">>> Total Shipping Cost: $" + String.format("%.2f", totalShipping));

        cart.applyVisitor(taxVisitor);
        double totalTax = taxVisitor.getTotalTax();
        System.out.println(">>> Total Tax: $" + String.format("%.2f", totalTax));

        cart.applyVisitor(packingVisitor);
        String packingList = packingVisitor.getPackingList();
        System.out.println(">>> Final Packing List:\n" + packingList);

        // To add a new operation (e.g., LoyaltyPointsVisitor):
        // 1. Create LoyaltyPointsVisitor implements CartItemVisitor.
        // 2. Instantiate it: LoyaltyPointsVisitor loyaltyVisitor = new
        // LoyaltyPointsVisitor();
        // 3. Apply it: cart.applyVisitor(loyaltyVisitor);
        // NO changes needed to ShoppingCart or any CartItem classes!
    }
}
