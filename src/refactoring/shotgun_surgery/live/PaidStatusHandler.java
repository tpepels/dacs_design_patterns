package refactoring.shotgun_surgery.live;

public class PaidStatusHandler implements OrderStatusHandler {

    @Override
    public void validate(Order order) {
        if (order.getStatus() != OrderStatus.NEW) {
            throw new IllegalStateException("Wrong order status for PAID update.");
        }
    }

    @Override
    public void onEnter(Order order) {
        // Update order status in the database
        Database.updateOrderStatus(order.orderId, "PAID");
        // Send payment confirmation email
        EmailService.sendPaymentConfirmation(order.customerName, order.orderId);
    }

}
