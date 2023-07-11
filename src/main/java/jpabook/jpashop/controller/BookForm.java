package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
    // COMMON in Item
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    // ONLY Item
    private String author;
    private String isbn;
}
