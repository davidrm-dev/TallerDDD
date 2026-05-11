package uptc.edu.swii.customer.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uptc.edu.swii.customer.application.dto.CustomerRequest;
import uptc.edu.swii.customer.application.dto.CustomerResponse;
import uptc.edu.swii.customer.domain.Customer;
import uptc.edu.swii.customer.domain.CustomerId;
import uptc.edu.swii.customer.domain.CustomerRepository;
import uptc.edu.swii.customer.domain.Email;
import uptc.edu.swii.customer.domain.exception.DuplicateCustomerException;
import uptc.edu.swii.customer.infrastructure.publisher.DomainEventPublisher;

@Service
@Transactional
public class CreateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final DomainEventPublisher domainEventPublisher;

    public CreateCustomerUseCase(CustomerRepository customerRepository,
                                  DomainEventPublisher domainEventPublisher) {
        this.customerRepository = customerRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public CustomerResponse execute(CustomerRequest request) {
        if (customerRepository.findById(new CustomerId(request.getDocument())).isPresent()) {
            throw new DuplicateCustomerException("Customer with document " + request.getDocument() + " already exists");
        }

        Customer customer = Customer.create(
                new CustomerId(request.getDocument()),
                request.getFirstname(),
                request.getLastname(),
                request.getAddress(),
                request.getPhone(),
                Email.of(request.getEmail()),
                request.getUsername(),
                request.getPassword()
        );

        Customer savedCustomer = customerRepository.save(customer);
        domainEventPublisher.publish(savedCustomer);

        return mapToResponse(savedCustomer);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getDocument().getDocument(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getEmail().getValue()
        );
    }
}