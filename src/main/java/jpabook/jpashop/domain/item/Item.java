package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.service.item.UpdateItemDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "category_id" )
    private Category category;


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

    public void changeItem(UpdateItemDTO itemDTO) {
        this.setName(itemDTO.getName());
        this.setPrice(itemDTO.getPrice());
        this.setStockQuantity(itemDTO.getStockQuantity());
    }
}
