package jpabook.jpashop.service;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.CategoryRepository;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.service.item.UpdateBookDTO;
import jpabook.jpashop.service.item.UpdateItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional( readOnly = true )
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, UpdateItemDTO itemDTO) {
        Item item = itemRepository.findOne(itemId);
        item.changeItem(itemDTO);
    }

    @Transactional
    public void specifyBook(Long itemId, UpdateBookDTO bookDTO) {
        Book findBook = (Book)itemRepository.findOne(itemId);
        findBook.specifyBook(bookDTO);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }
}