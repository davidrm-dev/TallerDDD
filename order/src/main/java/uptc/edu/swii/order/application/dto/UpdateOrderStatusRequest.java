package uptc.edu.swii.order.application.dto;

import uptc.edu.swii.order.domain.valueobject.OrderStatus;

public class UpdateOrderStatusRequest {
    private OrderStatus status;

    public UpdateOrderStatusRequest() {}

    public UpdateOrderStatusRequest(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}