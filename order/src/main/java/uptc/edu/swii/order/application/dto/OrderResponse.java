package uptc.edu.swii.order.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import uptc.edu.swii.order.domain.model.Order;
import uptc.edu.swii.order.domain.valueobject.OrderItem;

public class OrderResponse {
    private String orderId;
    private String customerId;
    private List<OrderItemResponse> items;
    private String status;
    private double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderResponse() {}

    public OrderResponse(String orderId, String customerId, List<OrderItemResponse> items,
                         String status, double total, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.status = status;
        this.total = total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static OrderResponse fromDomain(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return new OrderResponse(
                order.getId().getValue(),
                order.getCustomerId(),
                itemResponses,
                order.getStatus().name(),
                order.getTotal(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class OrderItemResponse {
        private String productId;
        private String productName;
        private int quantity;
        private double price;

        public OrderItemResponse() {}

        public OrderItemResponse(String productId, String productName, int quantity, double price) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
        }

        public String getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPrice() {
            return price;
        }
    }
}