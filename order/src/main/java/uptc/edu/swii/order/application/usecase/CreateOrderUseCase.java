package uptc.edu.swii.order.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uptc.edu.swii.order.application.dto.CreateOrderRequest;
import uptc.edu.swii.order.application.dto.OrderResponse;
import uptc.edu.swii.order.domain.model.Order;
import uptc.edu.swii.order.domain.repository.CustomerRegistryRepository;
import uptc.edu.swii.order.domain.repository.OrderRepository;
import uptc.edu.swii.order.domain.valueobject.OrderItem;

import java.util.List;

@Service
@Transactional
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final CustomerRegistryRepository customerRegistryRepository;

    public CreateOrderUseCase(OrderRepository orderRepository,
                               CustomerRegistryRepository customerRegistryRepository) {
        this.orderRepository = orderRepository;
        this.customerRegistryRepository = customerRegistryRepository;
    }

    public OrderResponse execute(CreateOrderRequest request) {
        if (!customerRegistryRepository.existsByCustomerId(request.getCustomerId())) {
            throw new IllegalStateException("Customer does not exist: " + request.getCustomerId());
        }

        List<OrderItem> items = request.getItems().stream()
                .map(dto -> new OrderItem(
                        dto.getProductId(),
                        dto.getProductName(),
                        dto.getQuantity(),
                        dto.getPrice()
                ))
                .toList();

        Order order = Order.create(request.getCustomerId(), items);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.fromDomain(savedOrder);
    }
}