package architectures.event_driven.consumers;

import architectures.event_driven.producers.Stock;

public interface Investor {
    void update(String eventType, Stock stock);
}
