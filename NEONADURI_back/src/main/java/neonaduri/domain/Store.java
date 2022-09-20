package neonaduri.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import neonaduri.domain.type.MainCategory;
import neonaduri.domain.type.MiddleCategory;
import neonaduri.domain.type.SubCategory;

import javax.persistence.*;

@Entity @ToString
@Getter
@Table(name = "store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id", referencedColumnName = "spot_id")
    private Spot spotId;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "main_cat", nullable = false)
    private String mainCat;

    @Column(name = "middle_cat", nullable = false)
    private String midCat;

    @Column(name = "sub_cat", nullable = false)
    private String subCategory;

    public Store(Spot spot, String storeName, String mainCat, String midCat, String subCategory){
        this.spotId = spot;
        this.storeName = storeName;
        this.mainCat = mainCat;
        this.midCat = midCat;
        this.subCategory = subCategory;
    }
}
