package jpabook.jpashop.repository.order.simpleQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    private final EntityManager em;

    public List<OrderSimpleQueryDTO> findOrderDtos() {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.order.simpleQuery.OrderSimpleQueryDTO(o.id, o.member.name, o.orderDate, o.status, o.delivery.address) " +
                                "from Order o " +
                                "join o.member m " +
                                "join o.delivery d", OrderSimpleQueryDTO.class)
                .getResultList();
    }
}
