import java.util.List;

public class BloatedOrder {
    private String orderId;
    private String customerName;
    private String customerAddress;
    private List<String> items;
    private double totalAmount;
    private boolean isPaid;
    private boolean isShipped;
    private boolean isDelivered;

    public BloatedOrder(String orderId, String customerName, String customerAddress, List<String> items,
            double totalAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.items = items;
        this.totalAmount = totalAmount;
        this.isPaid = false;
        this.isShipped = false;
        this.isDelivered = false;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public List<String> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isShipped() {
        return isShipped;
    }

    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public void addItem(String item, double price) {
        this.items.add(item);
        this.totalAmount += price;
    }

    public void removeItem(String item, double price) {
        this.items.remove(item);
        this.totalAmount -= price;
    }

    public void printOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Customer Address: " + customerAddress);
        System.out.println("Items: " + items);
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Paid: " + (isPaid ? "Yes" : "No"));
        System.out.println("Shipped: " + (isShipped ? "Yes" : "No"));
        System.out.println("Delivered: " + (isDelivered ? "Yes" : "No"));
    }

    public void processOrder() {
        if (!isPaid) {
            System.out.println("Order cannot be processed because it is not paid yet.");
            return;
        }
        System.out.println("Processing order...");
        // More processing logic here
    }

    public void shipOrder() {
        if (!isPaid) {
            System.out.println("Order cannot be shipped because it is not paid yet.");
            return;
        }
        if (isShipped) {
            System.out.println("Order is already shipped.");
            return;
        }
        System.out.println("Shipping order...");
        setShipped(true);
        // More shipping logic here
    }

    public void deliverOrder() {
        if (!isShipped) {
            System.out.println("Order cannot be delivered because it is not shipped yet.");
            return;
        }
        if (isDelivered) {
            System.out.println("Order is already delivered.");
            return;
        }
        System.out.println("Delivering order...");
        setDelivered(true);
        // More delivery logic here
    }
}