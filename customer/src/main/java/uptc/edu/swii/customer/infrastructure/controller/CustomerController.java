package uptc.edu.swii.customer.infrastructure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uptc.edu.swii.customer.application.dto.CustomerRequest;
import uptc.edu.swii.customer.application.dto.CustomerResponse;
import uptc.edu.swii.customer.application.usecases.CreateCustomerUseCase;
import uptc.edu.swii.customer.application.usecases.DeleteCustomerUseCase;
import uptc.edu.swii.customer.application.usecases.FindCustomerByIdUseCase;
import uptc.edu.swii.customer.application.usecases.GetAllCustomersUseCase;
import uptc.edu.swii.customer.application.usecases.UpdateCustomerUseCase;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    @Autowired
    public CustomerController(CreateCustomerUseCase createCustomerUseCase,
                              FindCustomerByIdUseCase findCustomerByIdUseCase,
                              GetAllCustomersUseCase getAllCustomersUseCase,
                              UpdateCustomerUseCase updateCustomerUseCase,
                              DeleteCustomerUseCase deleteCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(getAllCustomersUseCase.execute());
    }

    @GetMapping("/{document}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable String document) {
        CustomerResponse response = findCustomerByIdUseCase.execute(document);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        try {
            CustomerResponse createdCustomer = createCustomerUseCase.execute(customerRequest);
            return ResponseEntity.ok(createdCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{document}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable String document, @RequestBody CustomerRequest customerRequest) {
        try {
            CustomerResponse updatedCustomer = updateCustomerUseCase.execute(document, customerRequest);
            return ResponseEntity.ok(updatedCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{document}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String document) {
        boolean deleted = deleteCustomerUseCase.execute(document);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}


