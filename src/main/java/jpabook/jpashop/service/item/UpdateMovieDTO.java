package jpabook.jpashop.service.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateMovieDTO {
    private String director;
    private String actor;

    public UpdateMovieDTO(String director, String actor) {
        this.director = director;
        this.actor = actor;
    }
}
