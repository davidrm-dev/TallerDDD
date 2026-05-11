package uptc.edu.swii.customer.domain;

import uptc.edu.swii.customer.domain.exception.DuplicateCustomerException;
import uptc.edu.swii.customer.domain.exception.InvalidCustomerStateException;
import uptc.edu.swii.customer.shared.domain.AggregateRoot;

public class Customer extends AggregateRoot<CustomerId> {

    private CustomerId document;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
    private Email email;
    private String username;
    private String password;

    Customer(CustomerId document, String firstname, String lastname, String address,
              String phone, Email email, String username, String password) {
        super(document);
        this.document = document;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public static Customer create(CustomerId document, String firstname, String lastname,
                                   String address, String phone, Email email,
                                   String username, String password) {
        validateFirstName(firstname);
        validateLastName(lastname);
        validateAddress(address);
        validatePhone(phone);
        validateUsername(username);
        validatePassword(password);

        Customer customer = new Customer(document, firstname, lastname, address, phone, email, username, password);

        customer.addDomainEvent(new CustomerCreatedEvent(
            document.getDocument(),
            username,
            password
        ));

        return customer;
    }

    public void updatePersonalInfo(String firstname, String lastname, String address, String phone) {
        validateFirstName(firstname);
        validateLastName(lastname);
        validateAddress(address);
        validatePhone(phone);

        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
    }

    public void updateEmail(Email newEmail) {
        this.email = newEmail;
    }

    public void updateCredentials(String username, String password) {
        validateUsername(username);
        validatePassword(password);
        this.username = username;
        this.password = password;
    }

    public CustomerId getDocument() {
        return document;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private static void validateFirstName(String firstname) {
        if (firstname == null || firstname.isBlank()) {
            throw new InvalidCustomerStateException("First name cannot be empty");
        }
    }

    private static void validateLastName(String lastname) {
        if (lastname == null || lastname.isBlank()) {
            throw new InvalidCustomerStateException("Last name cannot be empty");
        }
    }

    private static void validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new InvalidCustomerStateException("Address cannot be empty");
        }
    }

    private static void validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new InvalidCustomerStateException("Phone cannot be empty");
        }
    }

    private static void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new InvalidCustomerStateException("Username cannot be empty");
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new InvalidCustomerStateException("Password cannot be empty");
        }
    }
}