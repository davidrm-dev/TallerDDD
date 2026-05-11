package uptc.edu.swii.customer.domain;

import uptc.edu.swii.customer.shared.domain.DomainEvent;

public class CustomerCreatedEvent extends DomainEvent {

    public static final String EVENT_TYPE = "CustomerCreated";
    private final String document;
    private final String username;
    private final String password;

    public CustomerCreatedEvent(String document, String username, String password) {
        super(EVENT_TYPE);
        this.document = document;
        this.username = username;
        this.password = password;
    }

    public String getDocument() {
        return document;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
