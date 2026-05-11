package uptc.edu.swii.order.domain.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderCreatedEvent {
    private final String eventType = "OrderCreated";
    private final String occurredOn;
    private final String orderId;
    private final String customerId;
    private final double total;

    public OrderCreatedEvent(String orderId, String customerId, double total) {
        this.occurredOn = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
    }

    public String getEventType() {
        return eventType;
    }

    public String getOccurredOn() {
        return occurredOn;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getTotal() {
        return total;
    }
}