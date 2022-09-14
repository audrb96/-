package neonaduri.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString
@Table(name = "region")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigungu", nullable = false)
    private String sigungu;

    @Column(name = "myeon", nullable = false)
    private String myeon;

    @Builder
    public Region(String sido, String sigungu, String myeon){
        this.sido = sido;
        this.sigungu = sigungu;
        this.myeon = myeon;
    }
}
