package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit() {
            Category category1 = createCategory("book");
            Category category2 = createCategory("movie");
            Category category3 = createCategory("album");
            em.persist(category1);
            em.persist(category2);
            em.persist(category3);

            dbInit1(category1);
            dbInit2(category1);
        }

        public void dbInit1(Category category) {
            Member member = createMember("userA", new Address("seoul", "1", "11111"));
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100, category);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100, category);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2(Category category) {
            Member member = createMember("userB", new Address("newyork", "2", "11111"));
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20000, 200, category);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000, 300, category);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

    }

    private static Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        return delivery;
    }

    private static Book createBook(String name, int price, int stockQuantity, Category category) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setCategory(category);
        return book;
    }

    private static Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        return member;
    }

    private static Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }
}