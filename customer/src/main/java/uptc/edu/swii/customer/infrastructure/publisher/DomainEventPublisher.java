package uptc.edu.swii.customer.infrastructure.publisher;

import org.springframework.stereotype.Component;

import uptc.edu.swii.customer.domain.Customer;
import uptc.edu.swii.customer.shared.domain.AggregateRoot;
import uptc.edu.swii.customer.shared.domain.DomainEvent;
import uptc.edu.swii.customer.shared.infrastructure.EventProducer;

@Component
public class DomainEventPublisher {

    private final EventProducer eventProducer;

    public DomainEventPublisher(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public void publish(AggregateRoot<?> aggregate) {
        for (DomainEvent event : aggregate.getDomainEvents()) {
            String topic = mapEventTypeToTopic(event.getEventType());
            eventProducer.sendEvent(topic, event);
        }
        aggregate.clearDomainEvents();
    }

    private String mapEventTypeToTopic(String eventType) {
        return switch (eventType) {
            case "CustomerCreated" -> "customer-created-login-auto";
            case "CustomerUpdated" -> "customer-created-login-auto";
            case "CustomerDeleted" -> "customer-created-login-auto";
            default -> "customer-created-login-auto";
        };
    }
}