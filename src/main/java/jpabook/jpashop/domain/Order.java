package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table( name = "orders" )                                           // DB에서 'ORDER'를 못 받음
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class Order {
    @Id @GeneratedValue
    @Column( name = "order_id" )
    private Long id;

    @ManyToOne( fetch = FetchType.LAZY )                            // 다대일
    @JoinColumn( name = "member_id" )                               // FK 설정
    private Member member;

    @OneToMany( mappedBy = "order", cascade = CascadeType.ALL )
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)   // 일대일은 둘 다 연관관계의 주인이 될 수 있다
    @JoinColumn( name = "delivery_id" )                             // order에서 delivery를 찾을 경우가 많으므로 연관관계의 주인으로
    private Delivery delivery;

    private LocalDateTime orderDate;                                // 주문 시간

    @Enumerated( EnumType.STRING )
    private OrderStatus status;                                     // 주문 상태 [ORDER, CANCEL]


    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    // 생성 메서드
                                                                // 주문 생성과 관련된 모든 로직을 응집해놓은 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem ... orderItems) {
        Order order = new Order();
        order.setMember(member);                                // 파라미터
        order.setDelivery(delivery);                            // 파라미터
        for (OrderItem orderItem : orderItems)                  // 파라미터
            order.addOrderItem(orderItem);
        order.setStatus(OrderStatus.ORDER);                     // 처음에는 주문 상태로 강제
        order.setOrderDate(LocalDateTime.now());                // 현재 시간

        return order;
    }


    // 비즈니스 로직
    public void cancel() {                                      // 주문 취소
        if (delivery.getStatus() == DeliveryStatus.COMP) {      // 배송이 완료되었을 경우
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);                     // 주문 상태 변경
        for (OrderItem orderItem : orderItems) {                // 한 주문에 엮인 여러 주문상품들 각각들을 취소하면서 수량을 원상복구 해야함
            orderItem.cancel();
        }
    }


    // 조회 로직
    public int getTotalPrice() {                                // 전체 주문 가격 반환
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}