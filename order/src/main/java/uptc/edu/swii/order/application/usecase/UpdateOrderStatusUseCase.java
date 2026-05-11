package uptc.edu.swii.order.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uptc.edu.swii.order.application.dto.OrderResponse;
import uptc.edu.swii.order.application.dto.UpdateOrderStatusRequest;
import uptc.edu.swii.order.domain.model.Order;
import uptc.edu.swii.order.domain.repository.OrderRepository;
import uptc.edu.swii.order.domain.valueobject.OrderId;
import uptc.edu.swii.order.domain.valueobject.OrderStatus;

@Service
@Transactional
public class UpdateOrderStatusUseCase {

    private final OrderRepository orderRepository;

    public UpdateOrderStatusUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse execute(String orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(new OrderId(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        switch (request.getStatus()) {
            case CONFIRMED -> order.confirm();
            case SHIPPED -> order.ship();
            case DELIVERED -> order.deliver();
            case CANCELLED -> order.cancel();
            default -> throw new IllegalArgumentException("Invalid status transition: " + request.getStatus());
        }

        Order updatedOrder = orderRepository.save(order);
        return OrderResponse.fromDomain(updatedOrder);
    }
}