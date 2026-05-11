package uptc.edu.swii.customer.application.dto;

public class CustomerLogin {
    
    private String document;
    private String username;
    private String password;

    public CustomerLogin(String document, String username, String password) {
        this.document = document;
        this.username = username;
        this.password = password;
    }

    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
