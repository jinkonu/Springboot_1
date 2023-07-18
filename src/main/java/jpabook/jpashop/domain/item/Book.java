package jpabook.jpashop.domain.item;

import jpabook.jpashop.service.item.UpdateBookDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book extends Item {
    private String author;
    private String isbn;

    public void specifyBook(UpdateBookDTO bookDTO) {
        this.setAuthor(bookDTO.getAuthor());
        this.setIsbn(bookDTO.getIsbn());
    }
}
