package jpabook.jpashop.domain.item;

import jpabook.jpashop.service.item.UpdateAlbumDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class Album extends Item {
    private String artist;
    private String etc;

    public void specifyAlbum(UpdateAlbumDTO updateAlbumDTO) {
        this.setArtist(updateAlbumDTO.getArtist());
        this.setEtc(updateAlbumDTO.getEtc());
    }
}