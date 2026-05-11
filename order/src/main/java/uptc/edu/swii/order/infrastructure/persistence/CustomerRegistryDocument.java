package uptc.edu.swii.order.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "customer_registry")
public class CustomerRegistryDocument {

    @Id
    private String customerId;

    @Field("registered_at")
    private java.time.LocalDateTime registeredAt;

    public CustomerRegistryDocument() {}

    public CustomerRegistryDocument(String customerId) {
        this.customerId = customerId;
        this.registeredAt = java.time.LocalDateTime.now();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public java.time.LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(java.time.LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}