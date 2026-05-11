package uptc.edu.swii.login.application.dto;

public class LoginResponse {
    private String customerId;
    private String username;
    private String message;

    public LoginResponse() {}

    public LoginResponse(String customerId, String username, String message) {
        this.customerId = customerId;
        this.username = username;
        this.message = message;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}