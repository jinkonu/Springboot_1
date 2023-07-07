package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    private Item createBook(String name, int orderPrice, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(orderPrice);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember("장군이", new Address("서울", "백범로", "111101"));
        Item book = createBook("안나 카레니나", 10000, 100);

        int count = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), count);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종료 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 '가격*수량'이다.", 10000 * count, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 98, book.getStockQuantity());
    }

    @Test( expected = NotEnoughStockException.class )
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember("장군이", new Address("서울", "백범로", "111101"));
        Item book = createBook("안나 카레니나", 10000, 100);

        int count = 111;

        // when
        orderService.order(member.getId(), book.getId(), count);

        // then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember("장군이", new Address("서울", "백범로", "111101"));
        Item book = createBook("안나 카레니나", 10000, 100);

        int count = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), count);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 복구되어야 한다.", 100, book.getStockQuantity());
    }
}