package uptc.edu.swii.customer.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uptc.edu.swii.customer.domain.CustomerId;
import uptc.edu.swii.customer.domain.CustomerRepository;

@Service
@Transactional
public class DeleteCustomerUseCase {

    private final CustomerRepository customerRepository;

    public DeleteCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean execute(String document) {
        CustomerId id = new CustomerId(document);
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}