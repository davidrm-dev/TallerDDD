package uptc.edu.swii.order.application.usecase;

import org.springframework.stereotype.Service;

import uptc.edu.swii.order.application.dto.OrderResponse;
import uptc.edu.swii.order.domain.model.Order;
import uptc.edu.swii.order.domain.repository.OrderRepository;
import uptc.edu.swii.order.domain.valueobject.OrderId;

import java.util.List;

@Service
public class GetCustomerOrdersUseCase {

    private final OrderRepository orderRepository;

    public GetCustomerOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponse> execute(String customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(OrderResponse::fromDomain)
                .toList();
    }

    public OrderResponse executeById(String orderId) {
        return orderRepository.findById(new OrderId(orderId))
                .map(OrderResponse::fromDomain)
                .orElse(null);
    }

    public List<OrderResponse> executeAll() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::fromDomain)
                .toList();
    }
}