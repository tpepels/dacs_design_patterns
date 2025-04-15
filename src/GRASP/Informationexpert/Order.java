package GRASP.Informationexpert;

import java.util.List;

class Product {
    private double price;

    public Product(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

// LineItem is the expert on how to calculate its price.
class LineItem {
    private Product product;
    private int quantity;

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double totalPrice() {
        return product.getPrice() * quantity;
    }
}

// Order is the expert on combining these totals.
class Order {
    private List<LineItem> items;

    public Order(List<LineItem> items) {
        this.items = items;
    }

    public double totalPrice() {
        return items.stream()
                .mapToDouble(LineItem::totalPrice)
                .sum();
    }
}
