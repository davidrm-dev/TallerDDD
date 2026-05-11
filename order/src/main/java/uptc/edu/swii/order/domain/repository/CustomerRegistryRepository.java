package uptc.edu.swii.order.domain.repository;

public interface CustomerRegistryRepository {
    boolean existsByCustomerId(String customerId);
    void registerCustomer(String customerId);
}