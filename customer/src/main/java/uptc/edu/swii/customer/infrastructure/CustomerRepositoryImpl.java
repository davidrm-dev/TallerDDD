package uptc.edu.swii.customer.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import uptc.edu.swii.customer.domain.Customer;
import uptc.edu.swii.customer.domain.CustomerId;
import uptc.edu.swii.customer.domain.CustomerRepository;
import uptc.edu.swii.customer.domain.Email;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    
    private final CustomerJpaRepository jpaRepository;

    public CustomerRepositoryImpl(CustomerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = new CustomerEntity(
            customer.getDocument().getDocument(),
            customer.getFirstname(),
            customer.getLastname(),
            customer.getAddress(),
            customer.getPhone(),
            customer.getEmail().getValue(),
            customer.getUsername(),
            customer.getPassword()
        );
        jpaRepository.save(entity);

        return customer;
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        return jpaRepository.findById(id.getDocument())
            .map(this::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.getValue())
            .map(this::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        return jpaRepository.findAll().stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(CustomerId id) {
        jpaRepository.deleteById(id.getDocument());
    }

    private Customer toDomain(CustomerEntity entity) {
        return Customer.create(
            new CustomerId(entity.getDocument()),
            entity.getFirstname(),
            entity.getLastname(),
            entity.getAddress(),
            entity.getPhone(),
            Email.of(entity.getEmail()),
            entity.getUsername(),
            entity.getPassword()
        );
    }
}
