package uptc.edu.swii.customer.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uptc.edu.swii.customer.application.dto.CustomerRequest;
import uptc.edu.swii.customer.application.dto.CustomerResponse;
import uptc.edu.swii.customer.domain.Customer;
import uptc.edu.swii.customer.domain.CustomerId;
import uptc.edu.swii.customer.domain.CustomerRepository;
import uptc.edu.swii.customer.domain.Email;

@Service
@Transactional
public class UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public UpdateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse execute(String document, CustomerRequest request) {
        Customer customer = customerRepository.findById(new CustomerId(document))
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setFirstname(request.getFirstname());
        customer.setLastname(request.getLastname());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());
        customer.setEmail(Email.of(request.getEmail()));

        Customer updatedCustomer = customerRepository.save(customer);
        
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