package uptc.edu.swii.login.domain;

import uptc.edu.swii.login.domain.valueobject.LoginId;
import uptc.edu.swii.login.domain.valueobject.Password;

public class Login {
    private LoginId id;
    private String customerId;
    private Password password;

    Login(LoginId id, String customerId, Password password) {
        this.id = id;
        this.customerId = customerId;
        this.password = password;
    }

    public static Login create(String customerId, String password) {
        LoginId id = new LoginId(customerId);
        Password pwd = new Password(password);
        return new Login(id, customerId, pwd);
    }

    public boolean authenticate(String inputPassword) {
        return this.password.getValue().equals(inputPassword);
    }

    public LoginId getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Password getPassword() {
        return password;
    }
}