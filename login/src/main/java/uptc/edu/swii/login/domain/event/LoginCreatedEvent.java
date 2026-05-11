package uptc.edu.swii.login.domain.event;

import java.time.LocalDateTime;

public class LoginCreatedEvent {
    private final String eventType = "LoginCreated";
    private final LocalDateTime occurredOn;
    private final String customerId;
    private final String username;
    private final String password;

    public LoginCreatedEvent(String customerId, String username, String password) {
        this.occurredOn = LocalDateTime.now();
        this.customerId = customerId;
        this.username = username;
        this.password = password;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}