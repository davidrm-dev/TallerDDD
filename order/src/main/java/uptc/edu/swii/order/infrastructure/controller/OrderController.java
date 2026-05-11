package uptc.edu.swii.order.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uptc.edu.swii.order.application.dto.CreateOrderRequest;
import uptc.edu.swii.order.application.dto.OrderResponse;
import uptc.edu.swii.order.application.dto.UpdateOrderStatusRequest;
import uptc.edu.swii.order.application.usecase.CreateOrderUseCase;
import uptc.edu.swii.order.application.usecase.GetCustomerOrdersUseCase;
import uptc.edu.swii.order.application.usecase.UpdateOrderStatusUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final GetCustomerOrdersUseCase getCustomerOrdersUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           UpdateOrderStatusUseCase updateOrderStatusUseCase,
                           GetCustomerOrdersUseCase getCustomerOrdersUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.getCustomerOrdersUseCase = getCustomerOrdersUseCase;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(getCustomerOrdersUseCase.executeAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId) {
        OrderResponse response = getCustomerOrdersUseCase.executeById(orderId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(@PathVariable String customerId) {
        return ResponseEntity.ok(getCustomerOrdersUseCase.execute(customerId));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            OrderResponse response = createOrderUseCase.execute(request);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable String orderId,
            @RequestBody UpdateOrderStatusRequest request) {
        try {
            OrderResponse response = updateOrderStatusUseCase.execute(orderId, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}