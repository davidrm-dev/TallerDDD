package uptc.edu.swii.customer.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uptc.edu.swii.customer.application.dto.CustomerRequest;
import uptc.edu.swii.customer.application.dto.CustomerResponse;
import uptc.edu.swii.customer.domain.Customer;
import uptc.edu.swii.customer.domain.CustomerCreatedEvent;
import uptc.edu.swii.customer.domain.CustomerId;
import uptc.edu.swii.customer.domain.CustomerRepository;
import uptc.edu.swii.customer.domain.Email;
import uptc.edu.swii.customer.infrastructure.CustomerEventProducer;

@Service
@Transactional
public class CreateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerEventProducer eventProducer;

    public CreateCustomerUseCase(CustomerRepository customerRepository, CustomerEventProducer eventProducer) {
        this.customerRepository = customerRepository;
        this.eventProducer = eventProducer;
    }

    public CustomerResponse execute(CustomerRequest request) {
        if (customerRepository.findById(new CustomerId(request.getDocument())).isPresent()) {
            throw new IllegalArgumentException("Customer already exists");
        }

        Customer customer = new Customer(
                new CustomerId(request.getDocument()),
                request.getFirstname(),
                request.getLastname(),
                request.getAddress(),
                request.getPhone(),
                Email.of(request.getEmail())
        );

        Customer savedCustomer = customerRepository.save(customer);

        // Disparar evento de kafka que maneja el registro del login:
        eventProducer.sendEvent("customer-created-login-auto", 
                new CustomerCreatedEvent(request.getDocument(), request.getUsername(), request.getPassword()));

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