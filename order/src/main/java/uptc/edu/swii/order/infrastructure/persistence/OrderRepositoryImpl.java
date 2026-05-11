package uptc.edu.swii.order.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import uptc.edu.swii.order.domain.model.Order;
import uptc.edu.swii.order.domain.repository.OrderRepository;
import uptc.edu.swii.order.domain.valueobject.OrderId;
import uptc.edu.swii.order.domain.valueobject.OrderItem;
import uptc.edu.swii.order.domain.valueobject.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMongoRepository mongoRepository;

    public OrderRepositoryImpl(OrderMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Order save(Order order) {
        OrderDocument doc = toDocument(order);
        mongoRepository.save(doc);
        return order;
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return mongoRepository.findById(id.getValue()).map(this::toDomain);
    }

    @Override
    public List<Order> findByCustomerId(String customerId) {
        return mongoRepository.findByCustomerId(customerId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAll() {
        return mongoRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(OrderId id) {
        mongoRepository.deleteById(id.getValue());
    }

    private OrderDocument toDocument(Order order) {
        OrderDocument doc = new OrderDocument();
        doc.setId(order.getId().getValue());
        doc.setCustomerId(order.getCustomerId());
        doc.setStatus(order.getStatus().name());
        doc.setCreatedAt(order.getCreatedAt());
        doc.setUpdatedAt(order.getUpdatedAt());

        List<OrderDocument.OrderItemDocument> items = order.getItems().stream()
                .map(item -> new OrderDocument.OrderItemDocument(
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();
        doc.setItems(items);

        return doc;
    }

    private Order toDomain(OrderDocument doc) {
        List<OrderItem> items = doc.getItems().stream()
                .map(item -> new OrderItem(
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return Order.reconstitute(
                new OrderId(doc.getId()),
                doc.getCustomerId(),
                items,
                OrderStatus.valueOf(doc.getStatus()),
                doc.getCreatedAt(),
                doc.getUpdatedAt()
        );
    }
}