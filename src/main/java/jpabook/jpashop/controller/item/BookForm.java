package jpabook.jpashop.controller.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
    // ONLY Book
    private String author;
    private String isbn;
}
