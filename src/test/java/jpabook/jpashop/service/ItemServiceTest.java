package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)                                        // JUnit 실행 시에 스프링과 엮을 것인지
@SpringBootTest                                                     // 스프링부트를 띄운 채로 테스트 실행
@Transactional                                                      // 트랜잭션 걸고 테스트 실행 후 끝나면 롤백
public class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    // @Rollback( value = false )
    public void 상품등록() throws Exception {
        // given
        Item item = new Book();
        item.setName("안나 카레니나");

        // when
        itemService.saveItem(item);

        // then
        String itemName = itemService.findOne(item.getId()).getName();
        assertEquals(item.getName(), itemName);
    }
}