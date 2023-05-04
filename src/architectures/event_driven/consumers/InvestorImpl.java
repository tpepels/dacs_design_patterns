package architectures.event_driven.consumers;

import architectures.event_driven.producers.Stock;

public class InvestorImpl implements Investor {
    private String name;

    public InvestorImpl(String name) {
        this.name = name;
    }

    @Override
    public void update(String eventType, Stock stock) {
        switch (eventType) {
            case "PRICE_CHANGED":
                System.out.println(name + " received price update: " + stock.getSymbol() + " - $" + stock.getPrice());
                break;
            case "NEWS_UPDATE":
                System.out.println(name + " received news update.");
                break;
            default:
                System.out.println(name + " received unknown event.");
        }
    }
}
