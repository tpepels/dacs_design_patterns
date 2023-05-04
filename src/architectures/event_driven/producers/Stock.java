package architectures.event_driven.producers;

import architectures.event_driven.EventBus;

public class Stock {
    private String symbol;
    private double price;
    private EventBus eventBus;

    public Stock(String symbol, double price, EventBus eventBus) {
        this.symbol = symbol;
        this.price = price;
        this.eventBus = eventBus;
    }

    public void setPrice(double price) {
        this.price = price;
        eventBus.publish("PRICE_CHANGED", this);
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}
