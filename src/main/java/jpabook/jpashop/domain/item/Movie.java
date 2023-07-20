package jpabook.jpashop.domain.item;

import jpabook.jpashop.service.item.UpdateMovieDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter @Setter
public class Movie extends Item {
    private String director;
    private String actor;

    public void specifyMovie(UpdateMovieDTO movieDTO) {
        this.setDirector(movieDTO.getDirector());
        this.setActor(movieDTO.getActor());
    }
}
