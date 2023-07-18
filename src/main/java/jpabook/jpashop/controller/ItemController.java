package jpabook.jpashop.controller;

import jpabook.jpashop.controller.item.AlbumForm;
import jpabook.jpashop.controller.item.BookForm;
import jpabook.jpashop.controller.item.ItemForm;
import jpabook.jpashop.controller.item.MovieForm;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.item.UpdateBookDTO;
import jpabook.jpashop.service.item.UpdateItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createItemForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String createItem(ItemForm form) {
        Long id;

        if (form.getCategory().equals("book")) {
            Book item = new Book();
            createBasicItem(form, item);
            item.setCategory(itemService.findCategoryByName("book"));
            itemService.saveItem(item);

            id = item.getId();
        }
        else if (form.getCategory().equals("movie")) {
            Movie item = new Movie();
            createBasicItem(form, item);
            item.setCategory(itemService.findCategoryByName("movie"));
            itemService.saveItem(item);

            id = item.getId();
        }
        else {
            Album item = new Album();
            createBasicItem(form, item);
            item.setCategory(itemService.findCategoryByName("album"));
            itemService.saveItem(item);

            id = item.getId();
        }

        return "redirect:/items/new/" + form.getCategory() + "/" + id;
    }

    private void createBasicItem(ItemForm form, Item item) {
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());
    }

    @GetMapping("/items/new/{category}/{id}")
    public String createForm(Model model,
                             @PathVariable("category") String category,
                             @PathVariable("id") Long id)
    {
        Item findItem = itemService.findOne(id);
        if (findItem.getCategory().getName().equals("book")) {
            model.addAttribute("form", new BookForm());
            return "items/createBookForm";
        }
        else if (findItem.getCategory().getName().equals("book")) {
            model.addAttribute("form", new MovieForm());
            return "items/createMovieForm";
        }
        else {
            model.addAttribute("form", new AlbumForm());
            return "items/createAlbumForm";
        }
    }

    @PostMapping("/items/new/book/{id}")
    public String createBook(@PathVariable("id") Long id, BookForm form) {
        UpdateBookDTO bookDTO = new UpdateBookDTO(form.getAuthor(), form.getIsbn());
        itemService.specifyBook(id, bookDTO);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{id}/edit")
    public String updateItemForm(@PathVariable("id") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        ItemForm form = new ItemForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{id}/edit")
    public String updateItem(@PathVariable("id") Long itemId, @ModelAttribute("form") ItemForm bookForm) {   // Model에서 넘어오는 attribute를 자바에서 받기 위해
        UpdateItemDTO itemDTO = new UpdateItemDTO(bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());
        itemService.updateItem(itemId, itemDTO);

        return "redirect:/items";
    }
}






