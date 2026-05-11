package uptc.edu.swii.customer.domain;

import uptc.edu.swii.customer.shared.domain.ValueObject;

public class CustomerId extends ValueObject {
    
    private final String document;

    public CustomerId(String document) {
        this.document = document;
    }

    public String getDocument() {
        return document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerId that = (CustomerId) o;
        return document.equals(that.document);
    }

    @Override
    public int hashCode() {
        return document.hashCode();
    }
}
