package refactoring.shotgun_surgery;
// In the original code, any change to the order processing workflow 

// (e.g., adding a new status or modifying the email content) would require changes 
// across multiple methods (markAsPaid, shipOrder, deliverOrder). 
// This is an example of shotgun surgery.

public class ShotgunOrder {
    private String orderId;
    private String customerName;
    private String customerAddress;
    private double totalAmount;
    private boolean isPaid;
    private boolean isShipped;
    private boolean isDelivered;

    // Other methods

    public void markAsPaid() {
        this.isPaid = true;
        // Update order status in the database
        Database.updateOrderStatus(this.orderId, "PAID");
        // Send payment confirmation email
        EmailService.sendPaymentConfirmation(this.customerName, this.orderId);
    }

    public void shipOrder() {
        if (!this.isPaid) {
            throw new IllegalStateException("Order cannot be shipped until it is paid.");
        }
        this.isShipped = true;
        // Update order status in the database
        Database.updateOrderStatus(this.orderId, "SHIPPED");
        // Send shipping confirmation email
        EmailService.sendShippingConfirmation(this.customerName, this.orderId);
    }

    public void deliverOrder() {
        if (!this.isShipped) {
            throw new IllegalStateException("Order cannot be delivered until it is shipped.");
        }
        this.isDelivered = true;
        // Update order status in the database
        Database.updateOrderStatus(this.orderId, "DELIVERED");
        // Send delivery confirmation email
        EmailService.sendDeliveryConfirmation(this.customerName, this.orderId);
    }
}

class Database {
    public static void updateOrderStatus(String orderId, String status) {
        // Code to update order status in the database
    }
}

class EmailService {
    public static void sendPaymentConfirmation(String customerName, String orderId) {
        // Code to send payment confirmation email
    }

    public static void sendShippingConfirmation(String customerName, String orderId) {
        // Code to send shipping confirmation email
    }

    public static void sendDeliveryConfirmation(String customerName, String orderId) {
        // Code to send delivery confirmation email
    }
}
