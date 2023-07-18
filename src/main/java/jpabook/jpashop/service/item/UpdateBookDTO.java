package jpabook.jpashop.service.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateBookDTO {
    private String author;
    private String isbn;

    public UpdateBookDTO(String author, String isbn) {
        this.author = author;
        this.isbn = isbn;
    }
}
