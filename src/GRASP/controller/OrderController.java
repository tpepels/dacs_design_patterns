package GRASP.controller;

// OrderController is the GRASP Controller — it handles external input.
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void placeOrder(String userId, List<LineItem> cartItems) {
        orderService.createOrder(userId, cartItems);
    }
}

// OrderService contains the domain logic.
class OrderService {
    public void createOrder(String userId, List<LineItem> cartItems) {
        // Validate, calculate total, persist, etc.
        Order order = new Order(cartItems);
        double total = order.totalPrice();
        // Save to DB, notify user, etc.
    }
}
