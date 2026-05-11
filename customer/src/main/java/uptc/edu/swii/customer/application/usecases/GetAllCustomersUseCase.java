package uptc.edu.swii.customer.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import uptc.edu.swii.customer.application.dto.CustomerResponse;
import uptc.edu.swii.customer.domain.Customer;
import uptc.edu.swii.customer.domain.CustomerRepository;

@Service
public class GetAllCustomersUseCase {

    private final CustomerRepository customerRepository;

    public GetAllCustomersUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponse> execute() {
        return customerRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
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