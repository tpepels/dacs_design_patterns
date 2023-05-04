package architectures.event_driven.producers;

import architectures.event_driven.EventBus;

public class News {
    private String headline;
    private EventBus eventBus;

    public News(String headline, EventBus eventBus) {
        this.headline = headline;
        this.eventBus = eventBus;
    }

    public void announceNews() {
        eventBus.publish("NEWS_UPDATE", null);
        System.out.println("News Update: " + headline);
    }
}
