package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null)       // persist() 이전에 item 인스턴스에는 id가 없기 때문에 if문으로 새로운 item이라는 보장을 받을 수 있다.
            em.persist(item);           // 새로운 객체로 등록한다.
        else
            em.merge(item);             // 이미 DB에 있고, update하는 것
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
