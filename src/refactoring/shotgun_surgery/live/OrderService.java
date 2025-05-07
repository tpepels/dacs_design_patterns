package refactoring.shotgun_surgery.live;

import java.util.Map;

public class OrderService {
    private static Map<OrderStatus, OrderStatusHandler> handlers = Map.of(OrderStatus.PAID, new PaidStatusHandler(),
            OrderStatus.SHIPPED, new ShippedStatusHandler(),
            OrderStatus.DELIVERED, new DeliveredStatusHandler());

    public static void changeStatus(Order order, OrderStatus orderStatus) {
        OrderStatusHandler handler = handlers.get(orderStatus);
        handler.validate(order);
        order.status = orderStatus;
        handler.onEnter(order);
    }
}

class ShippedStatusHandler implements OrderStatusHandler {

    @Override
    public void validate(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public void onEnter(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEnter'");
    }

}

class DeliveredStatusHandler implements OrderStatusHandler {
    @Override
    public void validate(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    public void onEnter(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onEnter'");
    }
}
