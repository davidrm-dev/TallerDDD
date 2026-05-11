package uptc.edu.swii.login.domain.valueobject;

import java.util.Objects;

public class LoginId {
    private final String customerId;

    public LoginId(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginId loginId = (LoginId) o;
        return customerId.equals(loginId.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}