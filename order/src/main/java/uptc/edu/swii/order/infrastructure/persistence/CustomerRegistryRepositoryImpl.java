package uptc.edu.swii.order.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import uptc.edu.swii.order.domain.repository.CustomerRegistryRepository;

@Repository
public class CustomerRegistryRepositoryImpl implements CustomerRegistryRepository {

    private final CustomerRegistryMongoRepository mongoRepository;

    public CustomerRegistryRepositoryImpl(CustomerRegistryMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public boolean existsByCustomerId(String customerId) {
        return mongoRepository.existsByCustomerId(customerId);
    }

    @Override
    public void registerCustomer(String customerId) {
        if (!mongoRepository.existsByCustomerId(customerId)) {
            mongoRepository.save(new CustomerRegistryDocument(customerId));
        }
    }
}