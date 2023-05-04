package architectures.event_driven;

import architectures.event_driven.consumers.Investor;
import architectures.event_driven.consumers.InvestorImpl;
import architectures.event_driven.producers.News;
import architectures.event_driven.producers.Stock;

/**
 * In this example, the Stock class represents the subject in the Observer
 * pattern, and the Investor interface represents the observer. When the stock
 * price changes, the Stock class notifies all registered investors through the
 * event bus. The InvestorImpl class implements the Investor interface
 * and provides a concrete implementation of the update method.
 * 
 * The EventBus class is introduced as a centralized event communication
 * channel. Components (in this case, Investor instances) can subscribe to
 * specific event types and will be notified when events of that type occur.
 * 
 * The Stock class uses the EventBus to publish "PRICE_CHANGED" events when the
 * stock price changes.
 * 
 * The News class has been added to represent market news. It also uses the
 * EventBus to publish "NEWS_UPDATE" events when there's a significant news
 * update.
 * 
 * To run the example, compile and execute the Main class. You'll see output
 * indicating that investors Alice and Bob receive updates when the stock price
 * changes. When Bob is removed as an investor, he no longer receives updates.
 * 
 * This example demonstrates an event-driven architecture, where the change in
 * stock price acts as an event. When the event occurs, all registered investors
 * are notified, allowing for a decoupled and reactive system design.
 */
public class Main {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        Stock appleStock = new Stock("AAPL", 150.00, eventBus);
        News marketNews = new News("Apple announces new iPhone", eventBus);

        Investor alice = new InvestorImpl("Alice");
        Investor bob = new InvestorImpl("Bob");

        eventBus.subscribe("PRICE_CHANGED", alice);
        eventBus.subscribe("PRICE_CHANGED", bob);
        eventBus.subscribe("NEWS_UPDATE", alice);
        eventBus.subscribe("NEWS_UPDATE", bob);

        appleStock.setPrice(155.00);
        marketNews.announceNews();

        appleStock.setPrice(160.00);

        eventBus.unsubscribe("PRICE_CHANGED", bob);
        eventBus.unsubscribe("NEWS_UPDATE", bob);

        appleStock.setPrice(170.00);
        marketNews.announceNews();
    }
}
