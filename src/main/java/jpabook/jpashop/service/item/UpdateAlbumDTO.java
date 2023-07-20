package jpabook.jpashop.service.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateAlbumDTO {
    private String artist;
    private String etc;

    public UpdateAlbumDTO(String artist, String etc) {
        this.artist = artist;
        this.etc = etc;
    }
}
