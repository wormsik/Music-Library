package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.utils.Constants;
import java.util.Arrays;
import javax.persistence.*;

/**
 * Entity Class representing one Album Image Created by olda on 30.10.2016.
 */
@Entity
@Table(name = "ART")
public class Art {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "id")
    private Long id;

    @Lob
    private byte[] image;

    @Column(nullable = false)
    private String imageType;

    @Column(nullable = false, length = Constants.INT_LENGTH_MEDIUM)
    private String imageName;



    public Art() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Art art = (Art) o;

        if (!Arrays.equals(image, art.image)) return false;
        if (imageType != null ? !imageType.equals(art.imageType) : art.imageType != null) return false;
        return imageName != null ? imageName.equals(art.imageName) : art.imageName == null;

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(image);
        result = 31 * result + (imageType != null ? imageType.hashCode() : 0);
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Art{" +
                "id=" + id +
                ", imageType='" + imageType + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
