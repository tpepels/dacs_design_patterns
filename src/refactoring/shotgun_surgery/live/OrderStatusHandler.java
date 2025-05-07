package refactoring.shotgun_surgery.live;

public interface OrderStatusHandler {
    void validate(Order order);

    void onEnter(Order order);
}
