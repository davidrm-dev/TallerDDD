package uptc.edu.swii.customer.shared.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class DomainEvent {
    private final String occurredOn;
    private final String eventType;

    protected DomainEvent(String eventType) {
        this.occurredOn = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.eventType = eventType;
    }

    public String getOccurredOn() {
        return occurredOn;
    }

    public String getEventType() {
        return eventType;
    }
}