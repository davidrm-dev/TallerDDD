package uptc.edu.swii.login.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import uptc.edu.swii.login.application.usecase.CreateLoginUseCase;

@Component
public class CustomerCreatedLoginListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCreatedLoginListener.class);

    private final CreateLoginUseCase createLoginUseCase;

    public CustomerCreatedLoginListener(CreateLoginUseCase createLoginUseCase) {
        this.createLoginUseCase = createLoginUseCase;
    }

    @KafkaListener(topics = "customer-created-login-auto", groupId = "login-group")
    public void onCustomerCreated(String message) {
        logger.info("Received customer created event: {}", message);
        try {
            String document = extractJsonValue(message, "document");
            String username = extractJsonValue(message, "username");
            String password = extractJsonValue(message, "password");

            if (document != null && username != null && password != null) {
                logger.info("Creating login for customer: {}", document);
                createLoginUseCase.execute(document, username, password);
                logger.info("Login created successfully for customer: {}", document);
            }
        } catch (Exception e) {
            logger.error("Error processing customer created event: {}", e.getMessage(), e);
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