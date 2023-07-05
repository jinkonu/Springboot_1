package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table( name = "orders" )                                           // DB에서 'ORDER'를 못 받음
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
}