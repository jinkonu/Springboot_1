package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Category_enum;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategorySearch {
    private Category_enum category;
}
