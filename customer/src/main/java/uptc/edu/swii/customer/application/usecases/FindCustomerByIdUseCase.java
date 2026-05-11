package uptc.edu.swii.customer.application.usecases;

import org.springframework.stereotype.Service;

import uptc.edu.swii.customer.application.dto.CustomerResponse;
import uptc.edu.swii.customer.domain.Customer;
import uptc.edu.swii.customer.domain.CustomerId;
import uptc.edu.swii.customer.domain.CustomerRepository;

@Service
public class FindCustomerByIdUseCase {

    private final CustomerRepository customerRepository;

    public FindCustomerByIdUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse execute(String document) {
        return customerRepository.findById(new CustomerId(document))
                .map(this::mapToResponse)
                .orElse(null);
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