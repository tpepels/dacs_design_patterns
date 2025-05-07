package refactoring.shotgun_surgery.live;

public class Order {
    private String orderId;
    private String customerName;
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }
}
