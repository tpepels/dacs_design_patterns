package refactoring.shotgun_surgery;

// To avoid shotgun surgery, we can use better encapsulation and modularization 
// by introducing new classes to handle specific responsibilities.

//  1.  Encapsulation: The responsibilities are divided among Order, OrderService, Database, and EmailService classes.
// 	2.	Single Responsibility Principle: Each class now has a single responsibility, making the code easier to maintain.
// 	3.	Reduced Shotgun Surgery: Changes to the order processing workflow can be made in the OrderService class, reducing the need to modify multiple methods across different classes.
// 	4.	Flexibility: It is easier to add new order statuses or change email content by modifying only the relevant parts of the OrderService and EmailService classes.

public class Order {
    private String orderId;
    private String customerName;
    private String customerAddress;
    private double totalAmount;
    private OrderStatus status;

    public Order(String orderId, String customerName, String customerAddress, double totalAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.NEW;
    }

    // Other methods

    public void markAsPaid() {
        if (this.status != OrderStatus.NEW) {
            throw new IllegalStateException("Order cannot be paid in its current state.");
        }
        this.status = OrderStatus.PAID;
        OrderService.updateOrder(this);
    }

    public void shipOrder() {
        if (this.status != OrderStatus.PAID) {
            throw new IllegalStateException("Order cannot be shipped until it is paid.");
        }
        this.status = OrderStatus.SHIPPED;
        OrderService.updateOrder(this);
    }

    public void deliverOrder() {
        if (this.status != OrderStatus.SHIPPED) {
            throw new IllegalStateException("Order cannot be delivered until it is shipped.");
        }
        this.status = OrderStatus.DELIVERED;
        OrderService.updateOrder(this);
    }
}

class OrderService {
    public static void updateOrder(Order order) {
        Database.updateOrderStatus(order.getOrderId(), order.getStatus().toString());
        EmailService.sendStatusUpdate(order);
    }
}

class Database {
    public static void updateOrderStatus(String orderId, String status) {
        // Code to update order status in the database
    }
}

class EmailService {
    public static void sendStatusUpdate(Order order) {
        switch (order.getStatus()) {
            case PAID:
                sendPaymentConfirmation(order.getCustomerName(), order.getOrderId());
                break;
            case SHIPPED:
                sendShippingConfirmation(order.getCustomerName(), order.getOrderId());
                break;
            case DELIVERED:
                sendDeliveryConfirmation(order.getCustomerName(), order.getOrderId());
                break;
            default:
                throw new IllegalArgumentException("Unknown order status");
        }
    }

    private static void sendPaymentConfirmation(String customerName, String orderId) {
        // Code to send payment confirmation email
    }

    private static void sendShippingConfirmation(String customerName, String orderId) {
        // Code to send shipping confirmation email
    }

    private static void sendDeliveryConfirmation(String customerName, String orderId) {
        // Code to send delivery confirmation email
    }
}

enum OrderStatus {
    NEW, PAID, SHIPPED, DELIVERED
}
