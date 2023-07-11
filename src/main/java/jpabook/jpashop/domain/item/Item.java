package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.service.UpdateItemDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = "dtype" )
@Getter @Setter
public abstract class Item {
    @Id @GeneratedValue
    @Column( name = "item_id" )
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany( mappedBy = "items" )
    private List<Category> categories = new ArrayList<Category>();


    // 비즈니스 로직
    public void addStockQuantity(int quantity) {        // 재고 수량 증가
        this.stockQuantity += quantity;
    }

    public void removeStockQuantity(int quantity) {     // 재고 수량 감소
        int left = this.stockQuantity - quantity;
        if (left < 0)
            throw new NotEnoughStockException("need more stock");

        this.stockQuantity = left;
    }


    public void change(UpdateItemDTO itemDTO) {
        this.setName(itemDTO.getName());
        this.setPrice(itemDTO.getPrice());
        this.setStockQuantity(itemDTO.getStockQuantity());
    }
}
