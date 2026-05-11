package uptc.edu.swii.order.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMongoRepository extends MongoRepository<OrderDocument, String> {
    List<OrderDocument> findByCustomerId(String customerId);
}