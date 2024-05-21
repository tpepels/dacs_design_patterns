package refactoring.bloater;

import java.util.List;

// To address these issues, we can refactor the class by separating responsibilities into smaller classes.
// Benefits of Refactored Design:

// 	1.	Separation of Concerns: Different classes handle different responsibilities (e.g., Customer, Item, OrderProcessor, ShipmentManager).
// 	2.	Maintainability: Smaller classes are easier to understand, test, and maintain.
// 	3.	Flexibility: Each class can be modified independently without affecting the others.
public class Order {
    private String orderId;
    private Customer customer;
    private List<Item> items;
    private PaymentStatus paymentStatus;
    private ShipmentStatus shipmentStatus;

    public Order(String orderId, Customer customer, List<Item> items) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = items;
        this.paymentStatus = PaymentStatus.UNPAID;
        this.shipmentStatus = ShipmentStatus.NOT_SHIPPED;
    }

    // Getter methods

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }
}

class Customer {
    private String name;
    private String address;

    // Constructor, getters, and setters
}

class Item {
    private String name;
    private double price;

    // Constructor, getters, and setters
}

enum PaymentStatus {
    PAID, UNPAID
}

enum ShipmentStatus {
    SHIPPED, NOT_SHIPPED, DELIVERED
}

class OrderProcessor {
    public void processOrder(Order order) {
        if (order.getPaymentStatus() != PaymentStatus.PAID) {
            System.out.println("Order cannot be processed because it is not paid yet.");
            return;
        }
        System.out.println("Processing order...");
        // More processing logic here
    }
}

class ShipmentManager {
    public void shipOrder(Order order) {
        if (order.getPaymentStatus() != PaymentStatus.PAID) {
            System.out.println("Order cannot be shipped because it is not paid yet.");
            return;
        }
        if (order.getShipmentStatus() == ShipmentStatus.SHIPPED) {
            System.out.println("Order is already shipped.");
            return;
        }
        System.out.println("Shipping order...");
        order.setShipmentStatus(ShipmentStatus.SHIPPED);
        // More shipping logic here
    }

    public void deliverOrder(Order order) {
        if (order.getShipmentStatus() != ShipmentStatus.SHIPPED) {
            System.out.println("Order cannot be delivered because it is not shipped yet.");
            return;
        }
        if (order.getShipmentStatus() == ShipmentStatus.DELIVERED) {
            System.out.println("Order is already delivered.");
            return;
        }
        System.out.println("Delivering order...");
        order.setShipmentStatus(ShipmentStatus.DELIVERED);
        // More delivery logic here
    }
}