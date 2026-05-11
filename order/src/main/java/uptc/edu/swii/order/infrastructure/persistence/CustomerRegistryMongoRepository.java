package uptc.edu.swii.order.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRegistryMongoRepository extends MongoRepository<CustomerRegistryDocument, String> {
    boolean existsByCustomerId(String customerId);
}