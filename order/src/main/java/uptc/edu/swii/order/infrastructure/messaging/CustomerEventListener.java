package uptc.edu.swii.order.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import uptc.edu.swii.order.infrastructure.persistence.CustomerRegistryRepositoryImpl;

@Component
public class CustomerEventListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomerEventListener.class);

    private final CustomerRegistryRepositoryImpl customerRegistryRepository;

    public CustomerEventListener(CustomerRegistryRepositoryImpl customerRegistryRepository) {
        this.customerRegistryRepository = customerRegistryRepository;
    }

    @KafkaListener(topics = "customer-created-login-auto", groupId = "order-group")
    public void onCustomerCreated(String message) {
        logger.info("Received customer event: {}", message);
        try {
            String customerId = extractJsonValue(message, "document");
            if (customerId != null) {
                logger.info("Registering customer in Order service: {}", customerId);
                customerRegistryRepository.registerCustomer(customerId);
                logger.info("Customer registered successfully: {}", customerId);
            }
        } catch (Exception e) {
            logger.error("Error processing customer event: {}", e.getMessage(), e);
        }
    }

    private String extractJsonValue(String json, String key) {
        String search = "\"" + key + "\"";
        int keyIndex = json.indexOf(search);
        if (keyIndex == -1) return null;
        int colonIndex = json.indexOf(":", keyIndex);
        if (colonIndex == -1) return null;
        int startIndex = colonIndex + 1;
        while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
            startIndex++;
        }
        if (startIndex >= json.length()) return null;
        char startChar = json.charAt(startIndex);
        if (startChar == '"') {
            int endQuote = json.indexOf('"', startIndex + 1);
            return endQuote > startIndex ? json.substring(startIndex + 1, endQuote) : null;
        } else {
            int endComma = json.indexOf(",", startIndex);
            int endBrace = json.indexOf("}", startIndex);
            int end = Math.min(endComma != -1 ? endComma : Integer.MAX_VALUE,
                              endBrace != -1 ? endBrace : Integer.MAX_VALUE);
            return end != Integer.MAX_VALUE ? json.substring(startIndex, end).trim() : null;
        }
    }
}