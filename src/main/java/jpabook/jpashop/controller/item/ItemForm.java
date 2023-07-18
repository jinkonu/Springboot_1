package jpabook.jpashop.controller.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemForm {
    // COMMON in Item
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    // category
    private String category;
}
