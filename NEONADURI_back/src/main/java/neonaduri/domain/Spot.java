package neonaduri.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "spot")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_id", nullable = false)
    private Long spotId;

    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "class_id")
    private Long classId;

    @OneToMany(mappedBy = "spotId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>();

    @Column(name = "spot_name", nullable = false)
    private String spotName;

    @Column(name = "lat", nullable = false)
    private Float lat;

    @Column(name = "lng", nullable = false)
    private Float lng;

    @Column(name = "tel")
    private String tel;

    @Column(name = "spot_image", nullable = false)
    @Lob
    private String spotImage;

    @Column(name = "spot_content")
    private String spotContent;

    @Builder
    public Spot(Long classId, Float lat, Float lng, Long regionId, String spotImage, String spotName, String tel){
        this.classId = classId;
        this.lat = lat;
        this.lng = lng;
        this.regionId = regionId;
        this.spotImage = spotImage;
        this.spotName = spotName;
        this.tel = tel;
    }

}
