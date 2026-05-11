package uptc.edu.swii.customer.domain;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(CustomerId id);
    Optional<Customer> findByEmail(Email email);
    List<Customer> findAll();
    void deleteById(CustomerId id);
}

