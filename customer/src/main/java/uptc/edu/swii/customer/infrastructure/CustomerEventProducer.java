package uptc.edu.swii.customer.infrastructure;

import org.springframework.stereotype.Component;
import uptc.edu.swii.customer.domain.CustomerCreatedEvent;
import uptc.edu.swii.customer.shared.infrastructure.EventProducer;

@Component
public class CustomerEventProducer extends EventProducer<CustomerCreatedEvent> {
    
}
