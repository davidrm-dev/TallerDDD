package uptc.edu.swii.order.domain.repository;

import java.util.List;
import java.util.Optional;

import uptc.edu.swii.order.domain.model.Order;
import uptc.edu.swii.order.domain.valueobject.OrderId;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId id);
    List<Order> findByCustomerId(String customerId);
    List<Order> findAll();
    void deleteById(OrderId id);
}