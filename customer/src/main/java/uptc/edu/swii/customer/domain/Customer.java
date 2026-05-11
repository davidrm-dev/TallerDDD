package uptc.edu.swii.customer.domain;

import uptc.edu.swii.customer.shared.domain.AggregateRoot;

public class Customer extends AggregateRoot<CustomerId> {
    
    private CustomerId document;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
    private Email email;

    public Customer(CustomerId document, String firstname, String lastname, String address, String phone, Email email) {
        super(document);
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public CustomerId getDocument() {
        return document;
    }

    public void setDocument(CustomerId document) {
        this.document = document;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
    


}
