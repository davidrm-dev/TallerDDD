package uptc.edu.swii.customer.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uptc.edu.swii.customer.application.dto.CustomerRequest;
import uptc.edu.swii.customer.application.dto.CustomerResponse;
import uptc.edu.swii.customer.domain.Customer;
import uptc.edu.swii.customer.domain.CustomerId;
import uptc.edu.swii.customer.domain.CustomerRepository;
import uptc.edu.swii.customer.domain.Email;
import uptc.edu.swii.customer.domain.exception.CustomerNotFoundException;
import uptc.edu.swii.customer.infrastructure.publisher.DomainEventPublisher;

@Service
@Transactional
public class UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final DomainEventPublisher domainEventPublisher;

    public UpdateCustomerUseCase(CustomerRepository customerRepository,
                                  DomainEventPublisher domainEventPublisher) {
        this.customerRepository = customerRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public CustomerResponse execute(String document, CustomerRequest request) {
        Customer customer = customerRepository.findById(new CustomerId(document))
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with document: " + document));

        customer.updatePersonalInfo(
                request.getFirstname(),
                request.getLastname(),
                request.getAddress(),
                request.getPhone()
        );
        customer.updateEmail(Email.of(request.getEmail()));

        Customer updatedCustomer = customerRepository.save(customer);
        domainEventPublisher.publish(updatedCustomer);

        return mapToResponse(updatedCustomer);
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