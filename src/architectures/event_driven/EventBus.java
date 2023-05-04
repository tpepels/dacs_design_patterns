package architectures.event_driven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import architectures.event_driven.consumers.Investor;
import architectures.event_driven.producers.Stock;

public class EventBus {
    private Map<String, List<Investor>> subscribers = new HashMap<>();

    public void subscribe(String eventType, Investor investor) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(investor);
    }

    public void unsubscribe(String eventType, Investor investor) {
        List<Investor> investors = subscribers.get(eventType);
        if (investors != null) {
            investors.remove(investor);
        }
    }

    public void publish(String eventType, Stock stock) {
        List<Investor> investors = subscribers.get(eventType);
        if (investors != null) {
            for (Investor investor : investors) {
                investor.update(eventType, stock);
            }
        }
    }
}
