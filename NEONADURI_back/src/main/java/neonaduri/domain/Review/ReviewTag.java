//Review 와 Tag를 다대다로 연결해주기 위한 브릿지 테이블
package neonaduri.domain.Review;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class ReviewTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bridge_id")
    private Long bridgeId;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}



