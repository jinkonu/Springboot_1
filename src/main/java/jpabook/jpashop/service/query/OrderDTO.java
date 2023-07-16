package jpabook.jpashop.service.query;

import jpabook.jpashop.api.OrderApiController;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class OrderDTO {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    // 새로운 필드지만 엔티티인, OrderItem
//        private List<OrderItem> orderItems;
    private List<OrderItemDTO> orderItems;

    public OrderDTO(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();

        // OrderItem은 엔티티이기 때문에 getter를 호출해서 실제 값을 들여와야 프록시가 대체됨
//            order.getOrderItems().stream().forEach(o -> o.getItem().getName());
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDTO(orderItem))
                .collect(toList());
    }
}
